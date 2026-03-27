<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Employees Form</title>
    <style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap');

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Poppins', sans-serif;
}

body {
    background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)),
                url('https://images.unsplash.com/photo-1551836022-d5d88e9218df') no-repeat center/cover;
    min-height: 100vh;
    padding: 20px;
    color: #333;
}

/* Container */
.container {
    background: rgba(255,255,255,0.95);
    backdrop-filter: blur(10px);
    border-radius: 12px;
    padding: 25px;
    box-shadow: 0 10px 30px rgba(0,0,0,0.3);
}

/* Header */
h2 {
    text-align: center;
    margin-bottom: 20px;
    color: #ffffff;
}

/* Buttons */
a.add-btn {
    display: inline-block;
    margin-bottom: 15px;
    padding: 10px 16px;
    background: linear-gradient(135deg, #4CAF50, #2ecc71);
    color: white;
    text-decoration: none;
    border-radius: 6px;
    font-weight: 500;
    transition: 0.3s;
}

a.add-btn:hover {
    transform: translateY(-2px);
}

/* Table */
table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
    overflow: hidden;
    border-radius: 10px;
}

th {
    background: #2c3e50;
    color: white;
}

th, td {
    padding: 10px;
    text-align: center;
}

tr {
    background-color: #ffffff;
    transition: background 0.2s ease, transform 0.1s ease;
}

/* Zebra striping */
tr:nth-child(even) {
    background-color: #f4f6f8;
}

/* Hover effect */
tr:hover {
    background-color: #e9eef3;
    transform: scale(1.01);
}

/* Buttons */
.action-btn {
    padding: 6px 12px;
    border-radius: 5px;
    border: none;
    color: white;
    cursor: pointer;
    font-size: 13px;
}

.edit-btn {
    background: #3498db;
}

.delete-btn {
    background: #e74c3c;
}

/* Search + Sort */
form {
    margin-bottom: 15px;
}

input, select {
    padding: 7px;
    border-radius: 5px;
    border: 1px solid #ccc;
}

input:focus, select:focus {
    outline: none;
    border-color: #4CAF50;
    box-shadow: 0 0 5px rgba(76,175,80,0.4);
}

/* Modal */
.modal {
    display: none;
    position: fixed;
    z-index: 1000;
    left: 0; top: 0;
    width: 100%; height: 100%;
    background: rgba(0,0,0,0.6);
}

.modal-content {
    background: #fff;
    margin: 5% auto;
    padding: 25px;
    width: 45%;
    border-radius: 10px;
    animation: fadeIn 0.4s ease;
}

@keyframes fadeIn {
    from { opacity: 0; transform: translateY(20px);}
    to { opacity: 1; transform: translateY(0);}
}

.close {
    float: right;
    font-size: 24px;
    cursor: pointer;
}

/* Form inside modal */
.form-group {
    margin-bottom: 12px;
}

.form-group label {
    font-weight: 500;
    margin-bottom: 4px;
    display: block;
}

.form-group input, .form-group select {
    width: 100%;
}

/* Save Button */
.save-btn {
    background: linear-gradient(135deg, #4CAF50, #2ecc71);
    border: none;
    padding: 10px;
    width: 100%;
    border-radius: 6px;
    color: white;
    cursor: pointer;
    font-weight: 500;
}

/* Pagination */
.pagination {
    margin-top: 20px;
    text-align: center;
}

.pagination a {
    padding: 8px 14px;
    margin: 5px;
    border-radius: 5px;
    background: #3498db;
    color: white;
    text-decoration: none;
}

/* Messages */
.success {
    color: green;
    margin-bottom: 10px;
}

.error {
    color: red;
    margin-bottom: 10px;
}
</style>
</head>

<body>

<h2>Employee List</h2>

<a class="add-btn" href="${pageContext.request.contextPath}/employees/create">
    Add Employee
</a>

<form action="${pageContext.request.contextPath}/employees/search"
      method="get"
      style="margin-bottom: 20px; display: flex; gap: 10px; align-items: center;">

    <label style="font-weight: bold; color:#ffffff">Search:</label>

    <input type="number"
           name="id"
           placeholder="Search by ID"
           value="${param.id}"
           style="padding: 6px; border-radius: 4px; border: 1px solid #ccc;">

    <button type="submit"
            style="padding: 6px 12px; border-radius: 4px; border: none; background: #2196F3; color: white; cursor: pointer;">
        Search
    </button>
</form>


<c:if test="${not empty searchError}">
    <div style="color: red; font-weight: bold; margin-bottom: 10px;">
        ${searchError}
    </div>
</c:if>


<c:if test="${not empty successMessage}">
    <div style="color: green; font-weight: bold; margin-bottom: 10px;">
        ${successMessage}
    </div>
</c:if>

<form method="get"
      action="${pageContext.request.contextPath}/employees"
      style="margin-bottom: 15px; display: inline-flex; align-items: center; gap: 8px;">

    <label for="sortBy" style="font-weight: bold; color:#ffffff">
        Sort By:
    </label>

    <select id="sortBy"
            name="sortBy"
            onchange="this.form.submit()"
            style="
                padding: 6px 10px;
                border-radius: 4px;
                border: 1px solid #ccc;
                
            ">
        <option value="id" ${empty param.sortBy || param.sortBy == 'id' ? 'selected' : ''}>
            ID
        </option>
        <option value="name" ${param.sortBy == 'name' ? 'selected' : ''}>
            Name
        </option>
    </select>

    <!-- Preserve direction if later added -->
    <input type="hidden"
           name="direction"
           value="${empty param.direction ? 'asc' : param.direction}">
</form>

<form action="${pageContext.request.contextPath}/employees/bulk-delete" 
      method="post">
<table>
    <tr>
    <th>
        <input type="checkbox" onclick="toggleAll(this)">
    </th>
        <th>ID</th>
        <th>Name</th>
        <th>DOB</th>
        <th>X Class</th>
        <th>XII Class</th>
        <th>UG</th>
        <th>PG</th>
        <th>Current Address</th>
        <th>Permanent Address</th>
        <th>State</th>
        <th>Action</th>
    </tr>

    <c:forEach items="${employees}" var="emp">
        <tr>
        <td>
        <input type="checkbox" name="ids" value="${emp.id()}">
    </td>
            <td>${emp.id()}</td>
            <td>${emp.name()}</td>
            <td>${emp.dob()}</td>
            <td>${emp.education().tenthPercentage()}</td>
            <td>${emp.education().twelvethPercentage()}</td>
            <td>${emp.education().graduationCGPA()}</td>
            <td>${emp.education().postGraduationCGPA()}</td>
            <td>${emp.postal().currentAddress()}</td>
            <td>${emp.postal().permanentAddress()}</td>
            <td>${emp.postal().states().displayName}</td>
            

            <td class="action-cell">
                <button type="button" class="action-btn edit-btn" 
                        onclick="openEditModal(this)"
                        data-id="${emp.id()}"
                        data-name="${emp.name()}"
                        data-dob="${emp.dob()}"
                        data-tenth="${emp.education().tenthPercentage()}"
                        data-twelveth="${emp.education().twelvethPercentage()}"
                        data-ug="${emp.education().graduationCGPA()}"
                        data-pg="${emp.education().postGraduationCGPA()}"
                        data-current="${emp.postal().currentAddress()}"
                        data-permanent="${emp.postal().permanentAddress()}"
                        data-state="${emp.postal().states()}"
                        >
                    Edit
                </button>

            </td>
        </tr>
    </c:forEach>
</table>
    <div style="margin-top:15px;">
        <button type="submit" 
                class="action-btn delete-btn"
                onclick="return confirm('Delete selected employees?')">
            Delete
        </button>
    </div>
</form>


<div id="editEmployeeModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h3>Edit Employee</h3>
        
        <form action="${pageContext.request.contextPath}/employees/create" method="post">
            
            <input type="hidden" id="modal-id" name="id">

            <div class="form-group">
                <label>Name</label>
                <input type="text" id="modal-name" name="name" 
                >
            </div>
            
            <div class="form-group">
    <label>Date of Birth</label>
    <input type="date" id="modal-dob" name="dob">
</div>
            

            <div style="display: flex; gap: 10px;">
                <div class="form-group" style="flex: 1;">
                    <label>X %</label>
                    <input type="number" step="0.01" id="modal-tenth" name="education.tenthPercentage">
                </div>
                <div class="form-group" style="flex: 1;">
                    <label>XII %</label>
                    <input type="number" step="0.01" id="modal-twelveth" name="education.twelvethPercentage">
                </div>
            </div>

            <div style="display: flex; gap: 10px;">
                <div class="form-group" style="flex: 1;">
                    <label>UG CGPA</label>
                    <input type="number" step="0.01" id="modal-ug" name="education.graduationCGPA">
                </div>
                <div class="form-group" style="flex: 1;">
                    <label>PG CGPA</label>
                    <input type="number" step="0.01" id="modal-pg" name="education.postGraduationCGPA">
                </div>
            </div>

            <div class="form-group">
                <label>Current Address</label>
                <input type="text" id="modal-current" name="postal.currentAddress">
            </div>

            <div class="form-group">
                <label>Permanent Address</label>
                <input type="text" id="modal-permanent" name="postal.permanentAddress">
            </div>
           
           
            <div class="form-group">
    <label>State</label>
    <select id="modal-state" name="postal.states" 
            style="width: 100%; padding: 8px; border-radius: 4px; border: 1px solid #ccc;">
        <option value="">-- Select State --</option>
        <c:forEach items="${states}" var="state">
            <option value="${state}">${state.displayName}</option>
        </c:forEach>
    </select>
</div>

            <button type="submit" class="save-btn">Update Changes</button>
        </form>
    </div>
</div>

<div style="margin-top: 20px; text-align: center;">

    <!-- Previous Button -->
    <c:if test="${currentPage > 0}">
    <a class="action-btn edit-btn"
       href="${pageContext.request.contextPath}/employees?page=${currentPage - 1}&sortBy=${empty param.sortBy ? 'id' : param.sortBy}&direction=${empty param.direction ? 'asc' : param.direction}">
        Previous
    </a>
</c:if>

    <!-- Page Number Display -->
    <span style="margin: 0 10px; color:#ffffff">
        Page ${currentPage + 1} of ${totalPages}
    </span>

    <c:if test="${currentPage < totalPages - 1}">
    <a class="action-btn edit-btn"
       href="${pageContext.request.contextPath}/employees?page=${currentPage + 1}&sortBy=${empty param.sortBy ? 'id' : param.sortBy}&direction=${empty param.direction ? 'asc' : param.direction}">
        Next
    </a>
</c:if>

</div>

<script>

function openAddModal() {
    // Clear all fields in the modal
    document.getElementById("modal-id").value = "";
    document.getElementById("modal-name").value = "";
    document.getElementById("modal-state").value = "";
    
    // ... clear other fields ...
    
    document.getElementById("editEmployeeModal").style.display = "block";
    document.querySelector("#editEmployeeModal h3").innerText = "Add New Employee";
}
    // 1. Function to Open Modal and Populate Data
    function openEditModal(button) {
        // Get the modal
        var modal = document.getElementById("editEmployeeModal");
        
        // Populate fields from data-attributes
        document.getElementById("modal-id").value = button.getAttribute("data-id");
        document.getElementById("modal-name").value = button.getAttribute("data-name");
        document.getElementById("modal-dob").value = button.getAttribute("data-dob");
        
        // Education Fields
        document.getElementById("modal-tenth").value = button.getAttribute("data-tenth");
        document.getElementById("modal-twelveth").value = button.getAttribute("data-twelveth");
        document.getElementById("modal-ug").value = button.getAttribute("data-ug");
        document.getElementById("modal-pg").value = button.getAttribute("data-pg");
        
        // Postal Fields
        document.getElementById("modal-current").value = button.getAttribute("data-current");
        document.getElementById("modal-permanent").value = button.getAttribute("data-permanent");
        document.getElementById("modal-state").value = button.getAttribute("data-state");


        // Show the modal
        modal.style.display = "block";
    }

    // 2. Function to Close Modal
    function closeModal() {
        document.getElementById("editEmployeeModal").style.display = "none";
    }
    
    function toggleAll(source) {
        const checkboxes = document.querySelectorAll('input[name="ids"]');
        checkboxes.forEach(cb => cb.checked = source.checked);
    }


    // 3. Close modal if user clicks outside of it
    window.onclick = function(event) {
        var modal = document.getElementById("editEmployeeModal");
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>

</body>
</html>