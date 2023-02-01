let tableUsers = [];
let currentUser = "";
let deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
let editModal = new bootstrap.Modal(document.getElementById('editModal'));
let request = new Request("http://localhost:8080/api/users", {
    method: "GET",
    headers: {
        'Content-Type': 'application/json',
    },
});
getUsers()

function getUsers() {
    fetch(request).then(res =>
        res.json()).then(data => {
        tableUsers = [];
        if (data.length > 0) {
            data.forEach(user => {
                tableUsers.push(user)
            })
        } else {
            tableUsers = [];
        }

        showUsers(tableUsers);
    })
}


fetch("http://localhost:8080/api/users/current").then(res => res.json())
    .then(data => {
        currentUser = data;
        console.log(data)
        showOneUser(currentUser);
    })

function showUsers(table) {
    let temp = "";
    table.forEach(user => {
        temp += "<tr>"
        temp += "<td>" + user.id + "</td>"
        temp += "<td>" + user.name + "</td>"
        temp += "<td>" + user.lastName + "</td>"
        temp += "<td>" + user.age + "</td>"
        temp += "<td>" + user.country + "</td>"
        temp += "<td>" + user.username + "</td>"
        temp += "<td>" + user.roles.map(role => role.name.substring(5)) + "</td>"
        temp += "<td>" + `<a onclick='showEditModal(${user.id})' class="btn btn-outline-info" id="edit">Edit user</a>` + "</td>"
        temp += "<td>" + `<a onclick='showDeleteModal(${user.id})' class="btn btn-outline-danger" id="deleteButton">Delete user</a>` + "</td>"
        temp += "</tr>"
        document.getElementById("allUsersBody").innerHTML = temp;
    })
}

function getRoles(list) {
    let userRoles = [];
    for (let role of list) {
        if (role == 2 || role.id == 2) {
            userRoles.push("ADMIN");
        }
        if (role == 1 || role.id == 1) {
            userRoles.push("USER");
        }
    }
    return userRoles.join(" , ");
}

function showOneUser(user) {
    let temp = "";
    temp += "<tr>"
    temp += "<td>" + user.id + "</td>"
    temp += "<td>" + user.name + "</td>"
    temp += "<td>" + user.lastName + "</td>"
    temp += "<td>" + user.age + "</td>"
    temp += "<td>" + user.country + "</td>"
    temp += "<td>" + user.username + "</td>"
    temp += "<td>" + user.roles.map(role => role.name.substring(5)) + "</td>"
    temp += "</tr>"
    document.getElementById("oneUserBody").innerHTML = temp;
}

function rolesUser(event) {
    let rolesAdmin = {};
    let rolesUser = {};
    let roles = [];
    let allRoles = [];
    let sel = document.querySelector(event);
    for (let i = 0, n = sel.options.length; i < n; i++) {
        if (sel.options[i].selected) {
            roles.push(sel.options[i].value);
        }
    }
    if (roles.includes('2')) {
        rolesAdmin["id"] = 2;
        rolesAdmin["name"] = "ROLE_ADMIN";
        allRoles.push(rolesAdmin);
    }
    if (roles.includes('1')) {
        rolesUser["id"] = 1;
        rolesUser["name"] = "ROLE_USER";
        allRoles.push(rolesUser);
    }
    return allRoles;
}

document.getElementById('newUser').addEventListener('submit', addNewUser);

function addNewUser(form) {
    form.preventDefault();
    let newUserForm = new FormData(form.target);
    let user = {
        id: null,
        name: newUserForm.get('name'),
        lastName: newUserForm.get('lastName'),
        age: newUserForm.get('age'),
        country: newUserForm.get('country'),
        username: newUserForm.get('email'),
        password: newUserForm.get('password'),
        roles: rolesUser("#roles")
    };

    let req = new Request("http://localhost:8080/api/users", {
        method: 'POST',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json'
        }
    })
    fetch(req).then(() => getUsers())
    form.target.reset();
    const triggerE1 = document.querySelector('#v-pills-tabContent button[data-bs-target="#nav-home"]');
    bootstrap.Tab.getInstance(triggerE1).show()
}

function submitFormDeleteUser(id) {
    let request = new Request('http://localhost:8080/api/users/' + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    fetch(request).then(() => getUsers());
}


function showDeleteModal(id) {
    let request = new Request("http://localhost:8080/api/users/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    fetch(request).then(res => res.json()).then(deleteUser => {
            console.log(deleteUser);
            document.getElementById('idDel').setAttribute('value', deleteUser.id);
            document.getElementById('firstNameDel').setAttribute('value', deleteUser.name);
            document.getElementById('lastNameDel').setAttribute('value', deleteUser.lastName);
            document.getElementById('ageDel').setAttribute('value', deleteUser.age);
            document.getElementById('countryDel').setAttribute('value', deleteUser.country);
            document.getElementById('emailDel').setAttribute('value', deleteUser.username);
            document.getElementById('passwordDel').setAttribute('value', deleteUser.password);
            if (getRoles(deleteUser.roles).includes("USER") && getRoles(deleteUser.roles).includes("ADMIN")) {
                document.getElementById('rolesDel1').setAttribute('selected', 'true');
                document.getElementById('rolesDel2').setAttribute('selected', 'true');
            } else if (getRoles(deleteUser.roles).includes("USER")) {
                document.getElementById('rolesDel1').setAttribute('selected', 'true');
            } else if (getRoles(deleteUser.roles).includes("ADMIN")) {
                document.getElementById('rolesDel2').setAttribute('selected', 'true');
            }
            deleteModal.show();
        }
    );

    document.getElementById('deleteUser').addEventListener('submit', function (event) {
        event.preventDefault();
        submitFormDeleteUser(id);
        deleteModal.hide();
    });
}

function showEditModal(id) {
    let request = new Request("http://localhost:8080/api/users/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    fetch(request).then(res => res.json()).then(editUser => {
            document.getElementById('idRed').setAttribute('value', editUser.id);
            document.getElementById('nameRed').setAttribute('value', editUser.name);
            document.getElementById('lastNameRed').setAttribute('value', editUser.lastName);
            document.getElementById('ageRed').setAttribute('value', editUser.age);
            document.getElementById('countryRed').setAttribute('value', editUser.country);
            document.getElementById('emailRed').setAttribute('value', editUser.username);
            document.getElementById('passwordRed').setAttribute('value', editUser.password);
            if ((editUser.roles.map(role => role.id)) == 1 && ((editUser.roles.map(role => role.id)) == 2)) {
                document.getElementById('rolesRed1').setAttribute('selected', 'true');
                document.getElementById('rolesRed2').setAttribute('selected', 'true');
            } else if ((editUser.roles.map(role => role.id)) == 1) {
                document.getElementById('rolesRed1').setAttribute('selected', 'true');
            } else if (editUser.roles.map(role => role.id) == 2) {
                document.getElementById('rolesRed2').setAttribute('selected', 'true');
            }
            console.log(editUser)
            editModal.show();
        }
    );

    let editForm = document.getElementById('editUser');
    editForm.addEventListener('submit', submitFormEditUser);
}

function submitFormEditUser(event) {
    event.preventDefault();
    let redUserForm = new FormData(event.target);
    let user = {
        id: redUserForm.get('id'),
        name: redUserForm.get('name'),
        lastName: redUserForm.get('lastName'),
        age: redUserForm.get('age'),
        country: redUserForm.get('country'),
        username: redUserForm.get('email'),
        password: redUserForm.get('password'),
        roles: rolesUser("#rolesRed")
    }
    console.log(user);
    let request = new Request('http://localhost:8080/api/users', {
        method: 'PUT',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json',
        },
    });
    fetch(request).then(
        function (response) {
            console.log(response)
            getUsers();
            editModal.hide();
        });

}
