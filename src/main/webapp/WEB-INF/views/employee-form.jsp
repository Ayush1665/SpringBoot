<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Create Employee</title>

    <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap');

    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: 'Poppins', sans-serif;
    }

    body {
    min-height: 100vh;
    background: linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)),
                url('https://images.unsplash.com/photo-1521791136064-7986c2920216') no-repeat center/cover;
    
    display: flex;
    justify-content: center;
    align-items: flex-start;   
    
    padding: 40px 15px;
    overflow-y: auto;          
}

    .container {
        width: 520px;
        background: rgba(255, 255, 255, 0.95);
        backdrop-filter: blur(10px);
        padding: 30px 35px;
        border-radius: 12px;
        box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        animation: fadeIn 0.6s ease-in-out;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(20px); }
        to { opacity: 1; transform: translateY(0); }
    }

    h2 {
        text-align: center;
        margin-bottom: 20px;
        color: #2c3e50;
        font-weight: 600;
    }

    h3 {
        margin-top: 25px;
        margin-bottom: 10px;
        color: #34495e;
        border-left: 4px solid #4CAF50;
        padding-left: 8px;
        font-size: 16px;
    }

    label {
        display: block;
        margin-top: 12px;
        font-size: 13px;
        font-weight: 500;
        color: #555;
    }

    input[type="text"],
    input[type="date"],
    select {
        width: 100%;
        padding: 10px;
        margin-top: 6px;
        border-radius: 6px;
        border: 1px solid #ccc;
        font-size: 14px;
        transition: all 0.3s ease;
        background-color: #f9f9f9;
    }

    input:focus,
    select:focus {
        border-color: #4CAF50;
        outline: none;
        background-color: #fff;
        box-shadow: 0 0 5px rgba(76, 175, 80, 0.4);
    }

    .error {
        color: #e74c3c;
        font-size: 12px;
        margin-top: 4px;
        display: block;
    }

    .btn-group {
        margin-top: 25px;
        text-align: center;
    }

    button {
        padding: 12px 22px;
        border: none;
        border-radius: 6px;
        background: linear-gradient(135deg, #4CAF50, #2ecc71);
        color: #fff;
        font-size: 15px;
        font-weight: 500;
        cursor: pointer;
        transition: all 0.3s ease;
        box-shadow: 0 5px 15px rgba(0,0,0,0.2);
    }

    button:hover {
        transform: translateY(-2px);
        background: linear-gradient(135deg, #43a047, #27ae60);
        box-shadow: 0 8px 20px rgba(0,0,0,0.3);
    }

    button:active {
        transform: scale(0.98);
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