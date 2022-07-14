<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-ajaxy/1.6.1/scripts/jquery.ajaxy.js"></script>
</head>
<body>
	<%-- ${cc} --%>
	<form action="save" method="post">
		Country Name : <select name="country_id" id="country_id"
			class="form-control lab" required="required"
			onchange="findCountryId(this.value)">
			<option selected>-select-</option>
			<c:forEach items="${cc}" var="con">
				<option value="${con.country_id}">${con.country_name}</option>
			</c:forEach>
		</select> State Name : <select name="state_id" id="state_id"
			class="form-control lab" onchange="findStateId(this.value)"
			required="required">
			<option selected>-select-</option>
			<c:forEach items="${ss}" var="sts">
				<option value="${sts.state_id}">${sts.state_name}</option>
			</c:forEach>
		</select> District Name : <select name="district_id" id="district_id"
			class="form-control lab" required="required">
			<option selected>-select-</option>
			<c:forEach items="${dd}" var="dis">
				<option value="${dis.district_id}">${dis.district_name}</option>
			</c:forEach>
		</select>
		<div class="form-group">
			<label
				class="col-lg-2 col-md-3 col-sm-4 control-label no-padding-right"
				for="docPath">Upload Document</label>
			<div class="col-lg-3 col-md-4 col-sm-4">
				<span class="colon">:</span> <input type="file" name="dscertificate"
					id="dscertificate" class="form-control" style="width: 210px"
					onchange="fileValidation()"><span class="requireed">*</span>
				<span style="color: red; font-size: 12px">(.pdf Only &
					Maximum size 500KB)</span>
			</div>

			<div class="clearfix"></div>
		</div>
		<br>
		<br> <input type="submit" value="submit">
	</form>
	<br>
	<br>
	<a href="/view">view</a>
	<script type="text/javascript">
		function findCountryId(country_id) {
			//alert("country_id= "+country_id);		
			$.ajax({
				type : "GET",
				url : "find-Country-Id.htm",

				data : {
					"country_id" : country_id

				},
				success : function(response) {

					//alert(response);
					var html = "<option value=''>---Select---</option>";
					var val = JSON.parse(response);

					if (val != "" || val != null) {
						$.each(val, function(index, value) {
							html = html + "<option value="+value.State_id+" >"
									+ value.State_name + "</option>";
						});
					}
					$('#state_id').empty().append(html);
					$('#district_id').empty();
				},
				error : function(error) {

				}
			});
		}
		//find District By State Id
		function findStateId(state_id) {
			//alert("State-ID= "+state_id);		
			$.ajax({
				type : "GET",
				url : "find-State-Id.htm",

				data : {
					"state_id" : state_id

				},
				success : function(response) {

					//alert(response);
					var html = "<option value=''>---Select---</option>";
					var val = JSON.parse(response);

					if (val != "" || val != null) {
						$.each(val, function(index, value) {
							html = html
									+ "<option value="+value.district_id+" >"
									+ value.district_name + "</option>";
						});
					}
					$('#district_id').empty().append(html);

				},
				error : function(error) {

				}
			});
		}
	</script>
</body>
</html>