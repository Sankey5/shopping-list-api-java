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
      <tr ng-repeat="currentGroceryItem in groceryController.groceryItems">
        <td><span ng-bind="currentGroceryItem.name"></span></td>
        <td><span ng-bind="currentGroceryItem.quantity"></span></td>
        <td><span ng-bind="currentGroceryItem.measure"></span></td>
      </tr>
    </tbody>
  </table>
</div>

