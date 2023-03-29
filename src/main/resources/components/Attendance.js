$(document).ready(function() {

    // Load attendance data on page load
    loadAttendance();

    // Handle form submission
    $("form").submit(function(event) {
        event.preventDefault(); // prevent default form submission
    });

    // Add button click handler
    function addAttendance() {
        var id = $("#id").val();
        var checkInTime = $("#checkInTime").val();
        var checkOutTime = $("#checkOutTime").val();
        var justification = $("#justification").val();
        var data = {
            "id": id,
            "checkInTime": checkInTime,
            "checkOutTime": checkOutTime,
            "justification": justification
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/attendance/add",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(result) {
                alert("Attendance record added successfully");
                clearForm();
                loadAttendance();
            },
            error: function(xhr, status, error) {
                alert("Error adding attendance record: " + xhr.responseText);
            }
        });
    }

    // Update button click handler
    function updateAttendance() {
        var id = $("#id").val();
        var checkInTime = $("#checkInTime").val();
        var checkOutTime = $("#checkOutTime").val();
        var justification = $("#justification").val();
        var data = {
            "id": id,
            "checkInTime": checkInTime,
            "checkOutTime": checkOutTime,
            "justification": justification
        };
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/attendance/update/" + id,
            data: JSON.stringify(data),
            dataType: "json",
            success: function(result) {
                alert("Attendance record updated successfully");
                clearForm();
                loadAttendance();
            },
            error: function(xhr, status, error) {
                alert("Error updating attendance record: " + xhr.responseText);
            }
        });
    }

    // Delete button click handler
    function deleteAttendance() {
        var id = $("#id").val();
        $.ajax({
            type: "DELETE",
            url: "/attendance/delete/" + id,
            success: function(result) {
                alert("Attendance record deleted successfully");
                clearForm();
                loadAttendance();
            },
            error: function(xhr, status, error) {
                alert("Error deleting attendance record: " + xhr.responseText);
            }
        });
    }

    // Clear form inputs
    function clearForm() {
        $("#id").val("");
        $("#checkInTime").val("");
        $("#checkOutTime").val("");
        $("#justification").val("");
    }

    // Load attendance data from backend API
    function loadAttendance() {
        $.ajax({
            type: "GET",
            url: "/attendance/list",
            success: function(data) {
                var tableBody = $("#attendanceTableBody");
                tableBody.empty();
                $.each(data, function(index, attendance) {
                    var row = "<tr>"
                            + "<td>" + attendance.id + "</td>"
                            + "<td>" + attendance.checkInTime + "</td>"
                            + "<td>" + attendance.checkOutTime + "</td>"
                            + "<td>" + attendance.justification + "</td>"
                            + "<td><button onclick='editAttendance(" + attendance.id + ")'>Edit</button></td>"
                            + "</tr>";
                    tableBody.append(row);
                });
// Edit button click handler
    function editAttendance(id) {
        $.ajax({
            type: "GET",
            url: "/attendance/get/" + id,
            success: function(result) {
                $("#id").val(result.id);
                $("#checkInTime").val(result.checkInTime);
                $("#checkOutTime").val(result.checkOutTime);
                $("#justification").val(result.justification);
            },
            error: function(xhr, status, error) {
                alert("Error retrieving attendance record: " + xhr.responseText);
            }
        });
    }

});