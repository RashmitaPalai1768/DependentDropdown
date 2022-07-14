<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/fontawesome.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.4.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.min.css" />

<!-- jQuery library file -->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.5.1.js">
	
</script>

<!-- Datatable plugin JS library file -->
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js">
	
</script>
<style>
.dataTables_info {
	text-align: right;
	margin: 5px 0;
}

.dataTables_paginate {
	display: block;
	text-align: right;
}

.paginate_button {
	display: inline-block;
	padding: 5px 10px;
	background: #fff;
	color: #333;
	transition: .2s;
}

.paginate_button:hover {
	background: #eee;
}

.paginate_button.current {
	display: inline-block;
	padding: 5px 10px;
	background: #4285f4;
	color: #fff;
	box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .16), 0 2px 10px 0
		rgba(0, 0, 0, .12);
}
</style>

<style>
.dataTables_filter {
	float: right;
}

.dataTables_length {
	float: left;
}
</style>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.7.7/xlsx.core.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/xls/0.7.4-a/xls.core.min.js"></script>

</head>
<body>

	<label for="txtName"> Export To Excel:</label>
	<a title="Excel" id="anchExcel" runat="server"
		class="btn btn-inverse btn-lg" onclick="generateExcelsheet()"><i
		class="fa fa-file-excel-o" style="color: #54ff00;"></i></a>
	<label for="txtName"> Export To PDF:</label>
	<a title="Pdf" id="anchPdf" runat="server"
		class="btn btn-inverse btn-sm"><i class="fa fa-file-pdf-o"
		aria-hidden="true" style="color: red; font-size: 20px;"
		onclick="generatePdf()"></i></a>
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title">Employee Details</h4>
				</div>
				<div class="modal-body" align="left">

					<label>Country Name: </label> <input type="text" id="cname"
						class="no-outline" disabled> <br> <label>
						State Date: </label> <input type="text" id="sname" class="no-outline"
						disabled> <br> <label> District Name: </label> <input
						type="text" id="dname" class="no-outline" disabled> <br>




				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
	
	<div class="table-responsive" id="viewTable">
		<!-- <table class="table table-bordered table-sm table-striped"> -->
		<table class="mytable table table-bordered table-sm table-striped"
			id="dTable" border=1>
			<thead>
				<tr>
					<th width="40" class="text-center">Sl#</th>
					<th>Country Name</th>
					<th>State Name</th>
					<th>District Name</th>
					<th>Action</th>
					<!--  <th>check</th> -->

				</tr>
			</thead>
			<tbody>

				<c:forEach items="${mm}" varStatus="status" var="vv">
					<tr>
						<td>${status.count}</td>
						<td>${vv.country_id1.country_name}</td>
						<td>${vv.state_id1.state_name}</td>
						<td>${vv.district_id1.district_name}</td>
						<td><button data-toggle="modal" data-target="#myModal"
								onclick="show('${vv.country_id1.country_name}','${vv.state_id1.state_name}','${vv.district_id1.district_name}')">View</button>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<a href="/" class="previous">« Back</a>
	</div>
	<form action="ExcelReport.htm" id="excelsheetReportForm" method="POST">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<form action="pdfReport.htm" id="pdfReportForm" method="POST">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
</body>

<script>
	/* Initialization of datatable */
	$(document).ready(function() {
		$('#dTable').DataTable({
			//pageLength : 5,
			lengthMenu:[5,10,25,50,100],
			paging : true,
			searching : true,
			order : [ [ 0, "asc" ] ],
			columnDefs : [ {
				orderable : true,
				targets : [ 3 ]
			} ]

		});
	});
</script>
<script type="text/javascript">
	function generateExcelsheet() {
		alert("fghj");
		$("#excelsheetReportForm").submit();
	}

	function generatePdf() {
		alert("fghj");
		$("#pdfReportForm").submit();
	}
	function show(country, state, district) {
		document.getElementById("cname").value = country;
		document.getElementById("sname").value = state;
		document.getElementById("dname").value = district;
		//alert("dtrftghjk");
	}
</script>
</html>