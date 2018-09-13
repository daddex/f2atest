// The root URL for the RESTful services
var rootURL = "http://localhost:8080/employeeapp/rs/employee";

var currentWine;

// Retrieve wine list when application starts 
findAll();
renderFilterList();

// Nothing to delete in initial application state
$('#btnDelete').hide();
$('#concat').hide();
// Register listeners
$('#btnSearch').click(function() {
	search($('#searchKey').val());
	return false;
});

// Trigger search when pressing 'Return' on search key input field
$('#searchKey').keypress(function(e){
	if(e.which == 13) {
		search($('#searchKey').val());
		e.preventDefault();
		return false;
    }
});

$('#btnAdd').click(function() {
	newWine();
	return false;
});

$('#btnSave').click(function() {
	if ($('#employeeId').val() == '')
		addWine();
	else
		updateWine();
	return false;
});

$('#btnDelete').click(function() {
	deleteWine();
	return false;
});

$('#wineList a').live('click', function() {
	findById($(this).data('identity'));
});

// Replace broken images with generic wine bottle
$("img").error(function(){
  $(this).attr("src", "pics/generic.jpg");

});
$('input:radio[name="criteria"]').change(function() {
	
    if ($(this).val()=='sort') {
        $('#searchCriteria').attr('disabled', false);    
        $('#concat').val("");
    } 
    if ($(this).val()=='filter') {
        $('#searchCriteria').attr('disabled', false);
        $('#concat').val("");
    }else if ($(this).val()=='none') {
        $('#searchCriteria').attr('disabled', true);  
        $('#concat').val("");
    }
});
function search(searchKey) {
	//alert($('#criteria').val());
	//alert($('input:radio[name="criteria"]:checked').val());	
	$('#concat').val("");
	if($('input:radio[name="criteria"]:checked').val()=='sort'){
		if($('#searchCriteria').val()=='none'){
			alert("Please choose search option");
			return;
		}
		$('#concat').val("?sort="+$('#searchCriteria').val());
	}  
	if($('input:radio[name="criteria"]:checked').val()=='filter'){
		if($('#searchCriteria').val()=='none'){
			alert("Please choose search option");
			return;
		}
		$('#concat').val("?filter="+$('#searchCriteria').val()+":"+$('#searchKey').val());
	}   
	findAll();
	 
}

function newWine() {
	$('#btnDelete').hide();
	currentWine = {};
	renderDetails(currentWine); // Display empty form
}

function findAll() {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL+$('#concat').val(),
		dataType: "json", // data type of response
		success: renderList
	});
}

 

function findById(id) {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURL + '/id/' + id,
		dataType: "json",
		success: function(data){
			$('#btnDelete').show();
			console.log('findById success: ' + data.name);
			currentWine = data;
			renderDetails(currentWine);
		}
	});
}

function addWine() {
	console.log('addWine');	
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('Wine created successfully');
			$('#btnDelete').show();
			$('#employeeId').val(data.id);
			 location.reload();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('addWine error: ' + textStatus);
		}
	});
}

function updateWine() {
	console.log('updateWine');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('Employee updated successfully');
			 location.reload();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateEmployee error: ' + textStatus);
		}
	}); 
}

function deleteWine() {
	console.log('deleteWine');
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/id/' + $('#employeeId').val(),
		success: function(data, textStatus, jqXHR){
			alert('Employee deleted successfully');
			 location.reload();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteEmployee error');
		}
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data instanceof Array ? data : [data]);

	$('#wineList li').remove();
	$.each(list, function(index, wine) {
		$('#wineList').append('<li><a href="#" data-identity="' + wine.id + '">'+wine.name+'</a></li>');
	});
}
function renderFilterList() { 
	$('#filterList').append('Apply Filter:');
	$('#filterList').append('<li><a href="#" data-identity="name">name</a></li>');
	$('#filterList').append('<li><a href="#" data-identity="lastName">lastName</a></li>');
	$('#filterList').append('<li><a href="#" data-identity="addresse">addresse</a></li>');
	 
} 
function renderDetails(employee) {
	$('#employeeId').val(employee.id);
	$('#name').val(employee.name);
	$('#lastName').val(employee.lastName);
	$('#addresse').val(employee.addresse);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
	var employeeId = $('#employeeId').val();
	return JSON.stringify({
		"id": employeeId == "" ? null : employeeId, 
		"name": $('#name').val(), 
		"lastName": $('#lastName').val(),
		"addresse": $('#addresse').val()
		});
}
