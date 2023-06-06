const userUrl = "/api/admin/users";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userUrl,
    pageName: "user"
}
function getAccessTokenFromResponseHeaders(xhr) {
    var authorizationHeader = xhr.getResponseHeader("Authorization");
    if (authorizationHeader) {
        accessToken = authorizationHeader.split("Bearer ")[1];
    }
}
function enable(chkbox, id) {
    var enabled = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: userUrl + '/' + id,
        type: "PATCH",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer ' + getAccessTokenFromResponseHeaders());
        },
        data: "enabled=" + enabled
    }).done(function () {
        chkbox.closest("tr").attr("data-user-enabled", enabled);
        successNoty(enabled ? "User enabled" : "User disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
}

$(function () {
    makeEditable({
        "columns": [
            {
                "data": "firstName"
            },
            {
                "data": "lastName"
            },
            {
                "data": "displayName"
            },
            {
                "data": "email",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<a href='mailto:" + data + "'>" + data + "</a>";
                    }
                    return data;
                }
            },
            {
                "data": "roles"
            },
            {
                "data": "startpoint",
                "render": function (date, type, row) {
                    if (type === "display" && date) {
                        return date.substring(0, 10);
                    }
                    return date;
                }
            },
            {
                "data": "endpoint",
                "render": function (data, type, row) {
                    let enabled = data === null
                    if (type === "display") {
                        return "<input type='checkbox' " + (enabled ? "checked" : "") + " onclick='enable($(this)," + row.id + ");'/>";
                    }
                    return enabled;
                }
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (data.endpoint) {
                $(row).attr("data-user-enabled", false);
            }
        }
    });
});

