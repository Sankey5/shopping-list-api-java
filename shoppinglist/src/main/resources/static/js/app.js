
// HTMX custom extension

(function() {
    let api;

    htmx.defineExtension('put-Grocery-Items', {
        init: function(apiRef) {
            api = apiRef
        },

        onEvent: function(name, event) {
            if(name === 'htmx:configRequest')
                event.detail.headers['Content-Type'] = 'application/json'
        },

        encodeParameters: function(xhr, parameters, elt) {
            xhr.overrideMimeType('text/json');

            const vals = api.getExpressionVars(elt)
            let obj = setRecipeParameters(elt);
            console.log("Object before sending: ", obj);

            return JSON.stringify(obj);
        }
    })
})()

// Event handler helper methods

function setRecipeParameters(sourceElement) {
    let closestRow = sourceElement.closest('tr');
    let closestGroceryItemRows = closestRow.querySelectorAll('table.grocery-item-table tr');

    let paramArray = [];

    for(i = 0; i < closestGroceryItemRows.length; i++) {
        let currRow = closestGroceryItemRows[i].querySelectorAll('td');
        let obj = {};

        for(j = 0; j < currRow.length; j++) {
            switch(j) {
                case 0:
                    obj[currRow[j].classList[0]] = parseInt(currRow[j].innerText);
                    break;
                case 2:
                    obj[currRow[j].classList[0]] = parseFloat(currRow[j].innerText);
                    break;
                default:
                    obj[currRow[j].classList[0]] = currRow[j].innerText;
            }
        }
        obj.toString = function() {
            console.log('called toString in object');
            return "{id: this.id, name: this.name, quantity: this.quantity, measure:this.measure}"
        };

        paramArray[i] = obj;
    }

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