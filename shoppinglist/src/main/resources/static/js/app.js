function toggleRecipeDropDown(event) {
    let currentNode = event.currentTarget;
    let carrotNode = currentNode.querySelector('.carrot');
    let childTable = currentNode.querySelector('.grocery-item-table');

    toggleCarrotNode(carrotNode);

    if(childTable) {
        childTable.classList.toggle('not-visible');
    }
}

function toggleCarrotNode(carrotNode) {
    carrotNode.innerText = carrotNode.innerText == "⏴" ? "⏷" : "⏴";
}