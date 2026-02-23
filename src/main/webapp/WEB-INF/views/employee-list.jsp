<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Employees Form</title>
    <style>
        /* --- Existing Styles --- */
        body { font-family: Arial, sans-serif; margin: 20px; }
        h2 { margin-bottom: 10px; }
        a.add-btn { display: inline-block; margin-bottom: 15px; padding: 8px 14px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 4px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; text-align: center; border: 1px solid #ddd; }
        th { background-color: #f4f4f4; }
        tr:nth-child(even) { background-color: #fafafa; }
        .action-btn { padding: 6px 10px; margin: 2px; border: none; border-radius: 4px; color: white; font-size: 13px; cursor: pointer; text-decoration: none; display: inline-block;}
        .edit-btn { background-color: #2196F3; }
        .delete-btn { background-color: #f44336; }

        /* --- NEW MODAL STYLES --- */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; 
            z-index: 1000; 
            left: 0;
            top: 0;
            width: 100%; 
            height: 100%; 
            overflow: auto; 
            background-color: rgba(0,0,0,0.5); /* Black w/ opacity */
        }

        .modal-content {
            background-color: #fefefe;
            margin: 5% auto; /* 5% from the top and centered */
            padding: 20px;
            border: 1px solid #888;
            width: 50%; /* Could be more or less, depending on screen size */
            border-radius: 8px;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        /* Modal Form Styles */
        .form-group { margin-bottom: 15px; text-align: left; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .form-group input, .form-group textarea { width: 100%; padding: 8px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; }
        .save-btn { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; width: 100%; font-size: 16px; }
        .save-btn:hover { background-color: #45a049; }
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

    <label style="font-weight: bold;">Search:</label>

    <input type="number"
           name="id"
           placeholder="Search by ID"
           value="${param.id}"
           style="padding: 6px; border-radius: 4px; border: 1px solid #ccc;">

    <input type="text"
           name="name"
           placeholder="Search by Name"
           value="${param.name}"
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

    <label for="sortBy" style="font-weight: bold;">
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
    <span style="margin: 0 10px;">
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