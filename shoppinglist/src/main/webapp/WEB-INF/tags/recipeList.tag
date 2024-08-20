<div ng-app="Shopping_List_app" class="ng-cloak">
  <table style="color: white" class="table container" ng-controller="Grocery_List_Controller as groceryController">
    <thead>
      <tr>
        <th scope="col">Name</th>
        <th scope="col">Quantity</th>
        <th scope="col">Measure</th>
      </tr>
    </thead>
    <tbody>
      <tr ng-repeat="currentRecipe in groceryController.recipes">
        <td><span ng-bind="currentRecipe"></span></td>
      </tr>
    </tbody>
  </table>
</div>

