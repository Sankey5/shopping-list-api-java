
// HTMX Event handlers

document.body.addEventListener('htmx:configRequest', (event) => {
    let sourceElement = event.detail.elt;

    console.log(event.detail);
    console.log(sourceElement);

    if(sourceElement.localName == 'button' &&
        sourceElement.classList.contains('btn-put-grocery-list')
        ) {
        setRecipeParameters(event.detail.parameters, sourceElement);
    }

    console.log("New param list: ", event.detail.parameters);
});

// Event handler helper methods

function setRecipeParameters(parametersList, sourceElement) {
    let closestRow = sourceElement.closest('tr');
    let closestGroceryItemRows = closestRow.querySelectorAll('table.grocery-item-table tr');

    let paramArray = [];

    for(i = 0; i < closestGroceryItemRows.length; i++) {
        let currRow = closestGroceryItemRows[i].querySelectorAll('td');
        let obj = {};

        for(j = 0; j < currRow.length; j++) {
            obj[currRow[j].classList[0]] = currRow[j].innerText;
        }

        paramArray[i] = obj;
    }

    parametersList['groceryItemList'] = JSON.stringify(paramArray);
    console.log('parameter list before function exit: ', parametersList);
    return paramArray;
}

// UI Helper functions
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