
// HTMX custom extension

(function() {
    let api;

    htmx.defineExtension('post-Grocery-Items', {
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

            return JSON.stringify(obj);
        }
    })
})();

(function() {
    let api;

    htmx.defineExtension('json-enc-custom', {
        init: function(apiRef) {
            api = apiRef
        },

        onEvent: function(name, event) {
            if(name === 'htmx:configRequest')
                event.detail.headers['Content-Type'] = 'application/json'
        },

        encodeParameters: function(xhr, parameters, elt) {
            xhr.overrideMimeType('text/json');

            let encoded_parameters = encodingAlgorithm(parameters);
            return JSON.stringify(encoded_parameters);
        }
    })
})();

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
            return "{id: this.id, name: this.name, quantity: this.quantity, measure:this.measure}"
        };

        paramArray[i] = obj;
    }

    return paramArray;
}

function encodingAlgorithm(parameters) {
    let resultingObject = Object.create(null);
    const PARAM_NAMES = Object.keys(parameters);
    const PARAM_VALUES = Object.values(parameters);
    const PARAM_LENGTH = PARAM_NAMES.length;

    for (let param_index = 0; param_index < PARAM_LENGTH; param_index++) {
        let name = PARAM_NAMES[param_index];
        let value = PARAM_VALUES[param_index];
        let steps = JSONEncodingPath(name);
        let context = resultingObject;

        for (let step_index = 0; step_index < steps.length; step_index++) {
            let step = steps[step_index];
            context = setValueFromPath(context, step, value);
        }
    }

    let result = resultingObject;
    return result
}

function JSONEncodingPath(name) {
    let path = name;
    let original = path;
    const FAILURE = [{ "type": "object", "key": original, "last": true, "next_type": null }];
    let steps = Array();
    let first_key = String();
    for (let i = 0; i < path.length; i++) {
        if (path[i] !== "[") first_key += path[i];
        else break;
    }
    if (first_key === "") return FAILURE;
    path = path.slice(first_key.length);
    steps.push({ "type": "object", "key": first_key, "last": false, "next_type": null });
    while (path.length) {
        // [123...]
        if (/^\[\d+\]/.test(path)) {
            path = path.slice(1);
            let collected_digits = path.match(/\d+/)[0]
            path = path.slice(collected_digits.length);
            let numeric_key = parseInt(collected_digits, 10);
            path = path.slice(1);
            steps.push({ "type": "array", "key": numeric_key, "last": false, "next_type": null });
            continue
        }
        // [abc...]
        if (/^\[[^\]]+\]/.test(path)) {
            path = path.slice(1);
            let collected_characters = path.match(/[^\]]+/)[0];
            path = path.slice(collected_characters.length);
            let object_key = collected_characters;
            path = path.slice(1);
            steps.push({ "type": "object", "key": object_key, "last": false, "next_type": null });
            continue;
        }
        return FAILURE;
    }
    for (let step_index = 0; step_index < steps.length; step_index++) {
        if (step_index === steps.length - 1) {
            let tmp_step = steps[step_index];
            tmp_step["last"] = true;
            steps[step_index] = tmp_step;
        }
        else {
            let tmp_step = steps[step_index];
            tmp_step["next_type"] = steps[step_index + 1]["type"];
            steps[step_index] = tmp_step;
        }
    }
    return steps;
}

function setValueFromPath(context, step, value) {
    if (step.last) {
        context[step.key] = value;
    }

    //TODO: make merge functionality and file suport.

    //check if the context value already exists
    if (context[step.key] === undefined) {
        if (step.type === "object") {
            if (step.next_type === "object") {
                context[step.key] = {};
                return context[step.key];
            }
            if (step.next_type === "array") {
                context[step.key] = [];
                return context[step.key];
            }
        }
        if (step.type === "array") {
            if (step.next_type === "object") {
                context[step.key] = {};
                return context[step.key];
            }
            if (step.next_type === "array") {
                context[step.key] = [];
                return context[step.key];
            }
        }
    }
    else {
        return context[step.key];
    }
}

function getNumRecipeFormInputs() {
    let inputs = document.querySelectorAll('#recipe-form tbody tr');
    return inputs.length;
}

// UI Helper functions
function deleteRecipeInputField(event) {
    event.target.closest('tr').remove();
}

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