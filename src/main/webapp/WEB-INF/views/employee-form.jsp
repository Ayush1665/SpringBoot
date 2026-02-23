<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Create Employee</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f7fa;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 500px;
            margin: 40px auto;
            background-color: #ffffff;
            padding: 25px;
            border-radius: 6px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        
        .error {
    color: red;
    font-size: 13px;
    margin-top: 4px;
    display: block;
}
        

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        h3 {
            margin-top: 25px;
            border-bottom: 1px solid #ddd;
            padding-bottom: 5px;
            color: #333;
        }

        label {
            display: block;
            margin-top: 12px;
            font-weight: bold;
        }

        input[type="text"],
        select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 4px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        select {
            background-color: #fff;
        }

        .btn-group {
            margin-top: 25px;
            text-align: center;
        }

        button {
            padding: 10px 18px;
            border: none;
            border-radius: 4px;
            background-color: #4CAF50;
            color: #fff;
            font-size: 15px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
</head>

<body>

<div class="container">
    <h2>Create Employee</h2>

    <form action="${pageContext.request.contextPath}/employees/create" method="post">
    <label>Name</label>
    <input type="text" name="name" value="${employee.name}" />
    <c:if test="${errors.hasFieldErrors('name')}">
    <span class="error">${errors.getFieldError('name').defaultMessage}</span>
</c:if>

<label>Date of Birth</label>
<input type="date" name="dob" value="${employee.dob}" />

<c:if test="${errors.hasFieldErrors('dob')}">
    <span class="error">
        ${errors.getFieldError('dob').defaultMessage}
    </span>
</c:if>



    <h3>Education Details</h3>
    <label>10th class%</label>
    <input type="text" name="education.tenthPercentage" value="${employee.education.tenthPercentage}" />
    <c:if test="${errors.hasFieldErrors('education.tenthPercentage')}">
    <span class="error">${errors.getFieldError('education.tenthPercentage').defaultMessage}</span>
</c:if>

    <label>12th %</label>
    <input type="text" name="education.twelvethPercentage" value="${employee.education.twelvethPercentage}" />
    <c:if test="${errors.hasFieldErrors('education.twelvethPercentage')}">
    <span class="error">${errors.getFieldError('education.twelvethPercentage').defaultMessage}</span>
</c:if>

    <label>UG CGPA</label>
    <input type="text" name="education.graduationCGPA" value="${employee.education.graduationCGPA}" />
    <c:if test="${errors.hasFieldErrors('education.graduationCGPA')}">
    <span class="error">${errors.getFieldError('education.graduationCGPA').defaultMessage}</span>
</c:if>

    <label>PG CGPA</label>
    <input type="text" name="education.postGraduationCGPA" value="${employee.education.postGraduationCGPA}" />
    <c:if test="${errors.hasFieldErrors('education.postGraduationCGPA')}">
    <span class="error">${errors.getFieldError('education.postGraduationCGPA').defaultMessage}</span>
</c:if>

    <h3>Postal Address</h3>
    <label>Current Address</label>
    <input type="text" name="postal.currentAddress" value="${employee.postal.currentAddress}" />
    <c:if test="${errors.hasFieldErrors('postal.currentAddress')}">
    <span class="error">${errors.getFieldError('postal.currentAddress').defaultMessage}</span>
</c:if>

    <label>Permanent Address</label>
    <input type="text" name="postal.permanentAddress" value="${employee.postal.permanentAddress}" />
    <c:if test="${errors.hasFieldErrors('postal.permanentAddress')}">
    <span class="error">${errors.getFieldError('postal.permanentAddress').defaultMessage}</span>
</c:if>

    <label>State</label>
    <select name="postal.states">
        <option value="">-- Select State --</option>
        <c:forEach items="${states}" var="state">
            <option value="${state}" ${employee.postal.states == state ? 'selected' : ''}>${state.displayName}</option>
        </c:forEach>
    </select>
    
    <div class="btn-group">
        <button type="submit">Save</button>
    </div>
</form>
</div>
</body>
</html>
