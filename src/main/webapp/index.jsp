<!DOCTYPE HTML>
<html>
<head>
<title>Fis Antex Test</title>
<link rel="stylesheet" href="css/styles.css" />
</head>

<body>

	<div class="header">
		<table style="width: 80%">
			<tr>
				<td><b>Input Text</b></td>
				<td><b>Criteria</b></td>
				<td><b>Option Search</b></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><input type="text" id="searchKey" /><input type="text" id="concat" /></td>
				<td>
					No Criteria   <input type="radio" name="criteria" id="criteria" value="none" checked>
				    Sort   <input type="radio" name="criteria" id="criteria" value="sort">
					Filter <input type="radio" name="criteria" id="criteria" value="filter">
				</td>
				<td>
					 <select name="searchCriteria" id="searchCriteria" disabled>
						<option value="none">----</option>
						<option value="name">name</option>
						<option value="lastName">lastName</option>
						<option value="addresse">addresse</option>
					</select> 
				</td>
				<td><button id="btnSearch">Search</button></td>
				<td><button id="btnAdd">New Employee</button></td>
			</tr>			
		</table>

	</div>


	<div class="leftArea">
		<ul id="wineList"></ul>
	</div>

	<form id="wineForm">

		<div class="mainArea">

			<label>Id:</label> <input id="employeeId" name="id" type="text"
				disabled /> <label>Name:</label> <input type="text" id="name"
				name="name" required> <label>Last Name:</label> <input
				type="text" id="lastName" name="lastName" /> <label>Addresse:</label>
			<input type="text" id="addresse" name="addresse" />



			<button id="btnSave">Save</button>
			<button id="btnDelete">Delete</button>

		</div>



	</form>

	<script src="js/jquery-1.7.1.min.js"></script>
	<script src="js/main.js"></script>

</body>
</html>