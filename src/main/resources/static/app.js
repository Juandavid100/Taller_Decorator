/*
 * Mopa-Mopa Studio - frontend
 * The browser only keeps the customer's choices. Every price, day count and
 * business rule comes from the Java backend, which builds the real decorator chain.
 */

const state = {
    catalog: null,
    pieceId: null,
    colorId: null,
    layers: [] // [{ type: "MOPA_MOPA_LAYER", color: "RED" }, ...] innermost first
};

const elements = {
    pieceList: document.getElementById("piece-list"),
    colorList: document.getElementById("color-list"),
    decorationList: document.getElementById("decoration-list"),
    layerStack: document.getElementById("layer-stack"),
    emptyStack: document.getElementById("empty-stack"),
    clearButton: document.getElementById("clear-button"),
    resultPanel: document.querySelector(".result"),
    errorBanner: document.getElementById("error-banner"),
    preview: document.getElementById("preview"),
    totalPrice: document.getElementById("total-price"),
    totalDays: document.getElementById("total-days"),
    description: document.getElementById("description"),
    breakdownBody: document.getElementById("breakdown-body"),
    javaExpression: document.getElementById("java-expression")
};

const pesos = new Intl.NumberFormat("es-CO", { style: "currency", currency: "COP", maximumFractionDigits: 0 });

/* Ring styles used by the preview for the layers that have no resin color */
const RING_STYLES = {
    GOLD_LEAF: { fill: "url(#gold-gradient)" },
    SILVER_LEAF: { fill: "url(#silver-gradient)" },
    PROTECTIVE_LACQUER: { fill: "rgba(255,255,255,0.55)", stroke: "#e9dcc6" },
    ARTISAN_SIGNATURE: { fill: "#f3e6cf", stroke: "#2b211b", dashed: true },
    AUTHENTICITY_CERTIFICATE: { fill: "#efe2c2", stroke: "#b23a2a", dots: true },
    GIFT_PACKAGING: { fill: "#c08a5b", ribbon: true }
};

/* ---------------- API ---------------- */

async function fetchJson(url) {
    const response = await fetch(url);
    const body = await response.json();
    if (!response.ok) {
        throw new Error(body.error || "Request failed");
    }
    return body;
}

function buildQuoteUrl() {
    const layers = state.layers
        .map(layer => layer.color ? `${layer.type}:${layer.color}` : layer.type)
        .join(",");
    const params = new URLSearchParams({ piece: state.pieceId, layers });
    return `/api/quote?${params.toString()}`;
}

let quoteRequestId = 0;

async function refreshQuote() {
    const requestId = ++quoteRequestId;
    try {
        const quote = await fetchJson(buildQuoteUrl());
        if (requestId !== quoteRequestId) return; // a newer request already answered
        showError(null);
        renderQuote(quote);
    } catch (error) {
        if (requestId !== quoteRequestId) return;
        showError(error.message);
    }
}

/* ---------------- helpers ---------------- */

function decorationById(id) {
    return state.catalog.decorations.find(decoration => decoration.id === id);
}

function colorById(id) {
    return state.catalog.colors.find(color => color.id === id);
}

function hasLayer(type) {
    return state.layers.some(layer => layer.type === type);
}

function createElement(tag, className, text) {
    const element = document.createElement(tag);
    if (className) element.className = className;
    if (text !== undefined) element.textContent = text;
    return element;
}

/* ---------------- render: choices ---------------- */

function renderPieces() {
    elements.pieceList.replaceChildren();
    state.catalog.pieces.forEach(piece => {
        const card = createElement("button", "piece-card");
        card.type = "button";
        card.setAttribute("role", "radio");
        card.setAttribute("aria-checked", String(piece.id === state.pieceId));
        card.append(
            createElement("span", "name", piece.name),
            createElement("span", "meta", `${pesos.format(piece.price)} · ${piece.days} days`)
        );
        card.addEventListener("click", () => {
            state.pieceId = piece.id;
            renderPieces();
            refreshQuote();
        });
        elements.pieceList.append(card);
    });
}

function renderColors() {
    elements.colorList.replaceChildren();
    state.catalog.colors.forEach(color => {
        const swatch = createElement("button", "swatch");
        swatch.type = "button";
        swatch.style.background = color.hex;
        swatch.title = color.name;
        swatch.setAttribute("aria-label", color.name);
        swatch.setAttribute("role", "radio");
        swatch.setAttribute("aria-checked", String(color.id === state.colorId));
        swatch.addEventListener("click", () => {
            state.colorId = color.id;
            renderColors();
        });
        elements.colorList.append(swatch);
    });
}

function ruleText(decoration) {
    if (decoration.requiresResinBase) return "Needs a resin layer first";
    if (decoration.mustBeLast) return "Always the outermost layer";
    if (decoration.repeatable) return "Can be repeated";
    return "";
}

function renderDecorations() {
    elements.decorationList.replaceChildren();
    const resinApplied = hasLayer("MOPA_MOPA_LAYER");
    state.catalog.decorations.forEach(decoration => {
        const button = createElement("button", "decoration-button");
        button.type = "button";
        button.append(
            createElement("span", "name", decoration.name),
            createElement("span", "meta", `+${pesos.format(decoration.price)} · +${decoration.days} d`)
        );
        const rule = ruleText(decoration);
        if (rule) button.append(createElement("span", "rule", rule));

        const alreadyUsed = !decoration.repeatable && hasLayer(decoration.id);
        const missingResin = decoration.requiresResinBase && !resinApplied;
        button.disabled = alreadyUsed || missingResin || state.layers.length >= 10;
        button.addEventListener("click", () => addLayer(decoration));
        elements.decorationList.append(button);
    });
}

/* ---------------- layer stack ---------------- */

function addLayer(decoration) {
    const layer = { type: decoration.id, color: decoration.requiresColor ? state.colorId : null };
    const last = state.layers[state.layers.length - 1];
    if (last && decorationById(last.type).mustBeLast) {
        state.layers.splice(state.layers.length - 1, 0, layer); // keep packaging outside
    } else {
        state.layers.push(layer);
    }
    onLayersChanged();
}

function moveLayer(index, offset) {
    const target = index + offset;
    if (target < 0 || target >= state.layers.length) return;
    [state.layers[index], state.layers[target]] = [state.layers[target], state.layers[index]];
    onLayersChanged();
}

function removeLayer(index) {
    state.layers.splice(index, 1);
    onLayersChanged();
}

function onLayersChanged() {
    renderLayerStack();
    renderDecorations();
    refreshQuote();
}

function layerLabel(layer) {
    const decoration = decorationById(layer.type);
    return layer.color ? `${decoration.name} (${colorById(layer.color).name})` : decoration.name;
}

function layerSwatch(layer) {
    if (layer.color) return colorById(layer.color).hex;
    const style = RING_STYLES[layer.type];
    if (layer.type === "GOLD_LEAF") return "#d4a72c";
    if (layer.type === "SILVER_LEAF") return "#b9c0c8";
    return style ? style.fill : "#ccc";
}

function renderLayerStack() {
    elements.layerStack.replaceChildren();
    elements.emptyStack.hidden = state.layers.length > 0;
    state.layers.forEach((layer, index) => {
        const item = createElement("li", "layer-item");
        const dot = createElement("span", "layer-dot");
        dot.style.background = layerSwatch(layer);

        const up = createElement("button", "icon-button", "↑");
        up.type = "button";
        up.title = "Move inward";
        up.disabled = index === 0;
        up.addEventListener("click", () => moveLayer(index, -1));

        const down = createElement("button", "icon-button", "↓");
        down.type = "button";
        down.title = "Move outward";
        down.disabled = index === state.layers.length - 1;
        down.addEventListener("click", () => moveLayer(index, 1));

        const remove = createElement("button", "icon-button", "✕");
        remove.type = "button";
        remove.title = "Remove layer";
        remove.addEventListener("click", () => removeLayer(index));

        item.append(dot, createElement("span", "layer-name", layerLabel(layer)), up, down, remove);
        elements.layerStack.append(item);
    });
}

/* ---------------- render: quote ---------------- */

function showError(message) {
    elements.errorBanner.hidden = !message;
    elements.errorBanner.textContent = message || "";
    elements.resultPanel.classList.toggle("invalid", Boolean(message));
}

function renderQuote(quote) {
    elements.totalPrice.textContent = pesos.format(quote.totalPrice);
    elements.totalDays.textContent = `${quote.totalDays} days`;
    elements.description.textContent = quote.description + ".";
    elements.javaExpression.textContent = formatExpression(quote.javaExpression);

    elements.breakdownBody.replaceChildren();
    quote.steps.forEach(step => {
        const row = document.createElement("tr");
        const className = createElement("td", "class-name", step.className);
        row.append(
            createElement("td", "", step.layer),
            className,
            createElement("td", "num", `+${pesos.format(step.addedPrice)}`),
            createElement("td", "num", `+${step.addedDays}`)
        );
        elements.breakdownBody.append(row);
    });

    renderPreview(quote);
}

/* Turns "new A(new B(new C()))" into an indented, readable block */
function formatExpression(expression) {
    let depth = 0;
    let output = "ArtisanPiece piece = ";
    for (let i = 0; i < expression.length; i++) {
        const char = expression[i];
        if (char === "(" && expression.startsWith("new ", i + 1)) {
            depth++;
            output += "(\n" + "    ".repeat(depth);
        } else {
            output += char;
        }
    }
    return output + ";";
}

/* ---------------- SVG preview: one ring per decorator ---------------- */

const SVG_NS = "http://www.w3.org/2000/svg";

function svg(tag, attributes) {
    const element = document.createElementNS(SVG_NS, tag);
    Object.entries(attributes).forEach(([key, value]) => element.setAttribute(key, value));
    return element;
}

function renderPreview(quote) {
    const preview = elements.preview;
    preview.replaceChildren();

    const defs = svg("defs", {});
    defs.append(
        linearGradient("gold-gradient", ["#f3d36b", "#c99a1e", "#f1cf5f", "#a97c12"]),
        linearGradient("silver-gradient", ["#eef1f4", "#a7aeb6", "#e2e6ea", "#8f969e"]),
        radialGradient("wood-gradient", "#b98352", "#7a4c27")
    );
    preview.append(defs);

    const center = 200;
    const baseRadius = 52;
    const ringWidth = 14;
    const decorations = quote.steps.slice(1);
    const layerTypes = state.layers.map(layer => layer.type);

    // Zoom the view box so the piece always fills the preview nicely
    const outerRadius = Math.max(110, baseRadius + ringWidth * decorations.length) + 10;
    preview.setAttribute("viewBox", `${center - outerRadius} ${center - outerRadius} ${outerRadius * 2} ${outerRadius * 2}`);

    // Draw from the outermost ring inward so inner rings sit on top
    for (let i = decorations.length - 1; i >= 0; i--) {
        const radius = baseRadius + ringWidth * (i + 1);
        const step = decorations[i];
        const style = RING_STYLES[layerTypes[i]] || {};
        const fill = step.colorHex || style.fill || "#ccc";
        preview.append(svg("circle", {
            cx: center, cy: center, r: radius,
            fill,
            stroke: style.stroke || "rgba(0,0,0,0.18)",
            "stroke-width": style.stroke ? 2 : 1,
            "stroke-dasharray": style.dashed ? "6 5" : "none"
        }));
        if (style.dots) {
            addDots(preview, center, radius - ringWidth / 2);
        }
        if (style.ribbon) {
            preview.append(svg("rect", { x: center - 6, y: center - radius, width: 12, height: radius * 2, fill: "#b23a2a", opacity: 0.85 }));
            preview.append(svg("rect", { x: center - radius, y: center - 6, width: radius * 2, height: 12, fill: "#b23a2a", opacity: 0.85 }));
        }
    }

    // The concrete component: plain wood in the middle
    preview.append(svg("circle", { cx: center, cy: center, r: baseRadius, fill: "url(#wood-gradient)", stroke: "#5b3a1f", "stroke-width": 2 }));
    [16, 28, 40].forEach(radius => {
        preview.append(svg("circle", { cx: center + 3, cy: center - 2, r: radius, fill: "none", stroke: "rgba(60,35,15,0.35)", "stroke-width": 1 }));
    });
    const label = svg("text", { x: center, y: center + 5, "text-anchor": "middle", fill: "#fff8ec", "font-size": 13, "font-family": "DM Sans, sans-serif", "font-weight": 700 });
    label.textContent = state.catalog.pieces.find(piece => piece.id === state.pieceId).name.split(" ").pop();
    preview.append(label);
}

function addDots(parent, center, radius) {
    for (let angle = 0; angle < 360; angle += 30) {
        const radians = angle * Math.PI / 180;
        parent.append(svg("circle", {
            cx: center + radius * Math.cos(radians),
            cy: center + radius * Math.sin(radians),
            r: 2.5, fill: "#b23a2a"
        }));
    }
}

function linearGradient(id, colors) {
    const gradient = svg("linearGradient", { id, x1: "0", y1: "0", x2: "1", y2: "1" });
    colors.forEach((color, index) => {
        gradient.append(svg("stop", { offset: `${(index / (colors.length - 1)) * 100}%`, "stop-color": color }));
    });
    return gradient;
}

function radialGradient(id, inner, outer) {
    const gradient = svg("radialGradient", { id });
    gradient.append(svg("stop", { offset: "0%", "stop-color": inner }), svg("stop", { offset: "100%", "stop-color": outer }));
    return gradient;
}

/* ---------------- start ---------------- */

async function init() {
    try {
        state.catalog = await fetchJson("/api/catalog");
        state.pieceId = state.catalog.pieces[0].id;
        state.colorId = state.catalog.colors[0].id;
        renderPieces();
        renderColors();
        renderDecorations();
        renderLayerStack();
        elements.clearButton.addEventListener("click", () => {
            state.layers = [];
            onLayersChanged();
        });
        await refreshQuote();
    } catch (error) {
        showError("Could not reach the Java server. Is it running? (" + error.message + ")");
    }
}

init();
