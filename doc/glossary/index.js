const root = document.getElementById('root');
const templateLayer = document.getElementById('layer-template');
const templateDefinition = document.getElementById('definition-template');

function render(json) {
    root.innerHTML = ``;

    json['layers'].forEach((layer) => {
        const layerCopy = templateLayer.content.cloneNode(true);
        layerCopy.querySelector('.layer__title').innerHTML = layer['title'];
        layerCopy.querySelector('.layer__comment').innerHTML = layer['comment'];

        const layerArticles = layerCopy.querySelector('.layer-articles');

        layer['definitions'].forEach((definition) => {
            const definitionCopy = templateDefinition.content.cloneNode(true);
            definitionCopy.querySelector('.definition__title').innerHTML = definition.title;
            definitionCopy.querySelector('.definition__comment').innerHTML = definition['comment'].replace('\r\n', '<br>');

            if (definition.types && definition.types.length > 0) {
                definitionCopy.querySelector('.definition__tags').innerHTML = `<li>${definition.types[0]}</li>`;
            }
            layerArticles.appendChild(definitionCopy);
        });
        root.appendChild(layerCopy);
    });
}

(function fetchLivingDoc() {
    fetch('./data.json')
        .then((response) => response.json())
        .then((data) => render(data));
})()

