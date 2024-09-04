const API_BASE_URL = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', function() {
    initializeDarkMode();
    loadOdontologos();
    loadPacientes();
    loadTurnos();
    setFormEventListeners();
});

// Inicializa el modo oscuro
function initializeDarkMode() {
    const toggleButton = document.getElementById('dark-mode-toggle');
    const body = document.body;

    if (localStorage.getItem('dark-mode') === 'enabled') {
        body.classList.add('dark-mode');
        toggleButton.textContent = '☀️ Modo Claro';
    }

    toggleButton.addEventListener('click', function() {
        body.classList.toggle('dark-mode');

        if (body.classList.contains('dark-mode')) {
            toggleButton.textContent = '☀️ Modo Claro';
            localStorage.setItem('dark-mode', 'enabled');
        } else {
            toggleButton.textContent = '🌙 Modo Oscuro';
            localStorage.setItem('dark-mode', 'disabled');
        }
    });
}

// Función genérica para realizar operaciones CRUD
function fetchAPI(url, method, data) {
    return fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: data ? JSON.stringify(data) : undefined
    }).then(response => response.json());
}

// Función para cargar la lista de odontólogos
function loadOdontologos() {
    fetchAPI(`${API_BASE_URL}/odontologo/buscartodos`, 'GET')
        .then(data => renderOdontologos(data))
        .catch(error => console.error('Error:', error));
}

function renderOdontologos(data) {
    const odontologoList = document.getElementById('odontologo-list');
    odontologoList.innerHTML = '';
    data.forEach(odontologo => {
        const row = `
            <tr data-id="${odontologo.id}">
                <td>${odontologo.id}</td>
                <td>${odontologo.apellido}</td>
                <td>${odontologo.nombre}</td>
                <td>${odontologo.matricula}</td>
                <td>
                    <button class="btn btn-sm btn-warning editar-btn-odontologo" data-bs-toggle="modal" data-bs-target="#editarModalOdontologo">Editar</button>
                    <button class="btn btn-sm btn-danger eliminar-btn-odontologo">Eliminar</button>
                </td>
            </tr>`;
        odontologoList.insertAdjacentHTML('beforeend', row);
    });
    addEventListeners('odontologo');
}

// Función para cargar la lista de pacientes
function loadPacientes() {
    fetchAPI(`${API_BASE_URL}/paciente/buscartodos`, 'GET')
        .then(data => renderPacientes(data))
        .catch(error => console.error('Error:', error));
}

function renderPacientes(data) {
    const pacienteList = document.getElementById('paciente-list');
    pacienteList.innerHTML = '';
    data.forEach(paciente => {
        const row = `
            <tr data-id="${paciente.id}">
                <td>${paciente.id}</td>
                <td>${paciente.apellido}</td>
                <td>${paciente.nombre}</td>
                <td>${paciente.dni}</td>
                <td>${paciente.fechaIngreso}</td>
                <td>${paciente.domicilio.calle}</td>
                <td>${paciente.domicilio.numero}</td>
                <td>${paciente.domicilio.localidad}</td>
                <td>${paciente.domicilio.provincia}</td>
                <td>
                    <button class="btn btn-sm btn-warning editar-btn-paciente" data-bs-toggle="modal" data-bs-target="#editarModalPaciente">Editar</button>
                    <button class="btn btn-sm btn-danger eliminar-btn-paciente">Eliminar</button>
                </td>
            </tr>`;
        pacienteList.insertAdjacentHTML('beforeend', row);
    });
    addEventListeners('paciente');
}


// Función para cargar la lista de turnos. NOTA: El backend se manejó con DTO.
function loadTurnos() {
    fetchAPI(`${API_BASE_URL}/turnos/buscartodos`, 'GET')
        .then(data => renderTurnos(data))
        .catch(error => console.error('Error:', error));
}

function renderTurnos(data) {
    const turnoList = document.getElementById('turno-list');
    turnoList.innerHTML = '';
    data.forEach(turno => {
        const row = `
            <tr data-id="${turno.id}">
                <td>${turno.id}</td>
                <td>${turno.pacienteResponseDto.apellido}  ${turno.pacienteResponseDto.nombre} </td>
                <td>${turno.odontologoResponseDto.apellido} ${turno.odontologoResponseDto.nombre}  </td>
                <td>${turno.fecha}</td>
                <td>
                    <button class="btn btn-sm btn-danger eliminar-btn-turno">Eliminar</button>
                </td>
            </tr>`;
        turnoList.insertAdjacentHTML('beforeend', row);
    });
    addEventListeners('turno');
}


// Función para añadir eventos a los botones de editar y eliminar
function addEventListeners(entity) {
    if (entity === 'odontologo') {
        document.querySelectorAll('.editar-btn-odontologo').forEach(button => {
            button.addEventListener('click', handleEditOdontologo);
        });

        document.querySelectorAll('.eliminar-btn-odontologo').forEach(button => {
            button.addEventListener('click', handleDeleteOdontologo);
        });
    } else if (entity === 'paciente') {
        document.querySelectorAll('.editar-btn-paciente').forEach(button => {
            button.addEventListener('click', handleEditPaciente);
        });

        document.querySelectorAll('.eliminar-btn-paciente').forEach(button => {
            button.addEventListener('click', handleDeletePaciente);
        });
    } else if (entity === 'turno') {

        document.querySelectorAll('.eliminar-btn-turno').forEach(button => {
            button.addEventListener('click', handleDeleteTurno);
        });
    }


}

// Función para manejar la edición de un odontólogo
function handleEditOdontologo() {
    const row = this.closest('tr');
    const id = row.getAttribute('data-id');
    document.getElementById('editar-id').value = id;
    document.getElementById('editar-apellido').value = row.children[1].textContent;
    document.getElementById('editar-nombre').value = row.children[2].textContent;
    document.getElementById('editar-matricula').value = row.children[3].textContent;
}

// Función para manejar la edición de un paciente
function handleEditPaciente() {
    const row = this.closest('tr');
    const id = row.getAttribute('data-id');
    document.getElementById('editar-paciente-id').value = id;
    document.getElementById('editar-paciente-apellido').value = row.children[1].textContent;
    document.getElementById('editar-paciente-nombre').value = row.children[2].textContent;
    document.getElementById('editar-paciente-dni').value = row.children[3].textContent;
    document.getElementById('editar-paciente-fechaIngreso').value = row.children[4].textContent;
    document.getElementById('editar-paciente-calle').value =  row.children[5].textContent;
    document.getElementById('editar-paciente-numero').value = row.children[6].textContent;
    document.getElementById('editar-paciente-localidad').value = row.children[7].textContent;
    document.getElementById('editar-paciente-provincia').value = row.children[8].textContent;

    // Hacer una solicitud a la API para obtener los detalles del paciente
    fetchAPI(`${API_BASE_URL}/paciente/buscar/${id}`, 'GET')
    .then(paciente => {
    // Asignar el ID del domicilio al campo oculto
        document.getElementById('editar-domicilio-paciente-id').value = paciente.domicilio.id;
    })
}

// Función para manejar la eliminación de un odontólogo
function handleDeleteOdontologo() {
    const id = this.closest('tr').getAttribute('data-id');
    if (confirm('¿Estás seguro de que deseas eliminar este odontólogo?')) {
        fetch(`${API_BASE_URL}/odontologo/eliminar/${id}`, {
            method: 'DELETE'
        })
        .then(response => response.ok ? response.text() : Promise.reject('Error al eliminar'))
        .then(message => {
            console.log(message);
            alert("Odontólogo eliminado con éxito");
            loadOdontologos();
        })
        .catch(error => console.error('Error:', error));
    }
}

// Función para manejar la eliminación de un paciente
function handleDeletePaciente() {
    const id = this.closest('tr').getAttribute('data-id');
    if (confirm('¿Estás seguro de que deseas eliminar este paciente?')) {
        fetch(`${API_BASE_URL}/paciente/eliminar/${id}`, {
            method: 'DELETE'
        })
        .then(response => response.ok ? response.text() : Promise.reject('Error al eliminar'))
        .then(message => {
            console.log(message);
            alert("Paciente eliminado con éxito");
            loadPacientes();
        })
        .catch(error => console.error('Error:', error));
    }
}

// Función para manejar la eliminación de un turno
function handleDeleteTurno() {
    const id = this.closest('tr').getAttribute('data-id');
    if (confirm('¿Estás seguro de que deseas eliminar este turno?')) {
        fetch(`${API_BASE_URL}/turnos/eliminar/${id}`, {
            method: 'DELETE'
        })
        .then(response => response.ok ? response.text() : Promise.reject('Error al eliminar'))
        .then(message => {
            console.log(message);
            alert("Turno eliminado con éxito");
            loadTurnos();
        })
        .catch(error => console.error('Error:', error));
    }
}



// Configura los eventos de los formularios
function setFormEventListeners() {
    // Odontólogo
    document.getElementById('odontologo-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const nuevoOdontologo = {
            apellido: document.getElementById('odontologo-apellido').value,
            nombre: document.getElementById('odontologo-nombre').value,
            matricula: document.getElementById('odontologo-matricula').value
        };
        fetchAPI(`${API_BASE_URL}/odontologo/guardar`, 'POST', nuevoOdontologo)
            .then(data => {
                console.log(data);
                alert("Odontólogo agregado con éxito");
                this.reset();
                loadOdontologos();
            })
            .catch(error => console.error('Error:', error));
    });

    document.getElementById('editar-odontologo-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const odontologoModificado = {
            id: document.getElementById('editar-id').value,
            apellido: document.getElementById('editar-apellido').value,
            nombre: document.getElementById('editar-nombre').value,
            matricula: document.getElementById('editar-matricula').value
        };
        fetchAPI(`${API_BASE_URL}/odontologo/modificar`, 'PUT', odontologoModificado)
            .then(message => {
                console.log(message);
                const editarModalOdontologo = bootstrap.Modal.getInstance(document.getElementById('editarModalOdontologo'));
                editarModalOdontologo.hide();
                alert("Odontólogo modificado con éxito");
                loadOdontologos();
            })
            .catch(error => console.error('Error:', error));
    });

    // Paciente
    document.getElementById('paciente-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const nuevoPaciente = {
            apellido: document.getElementById('paciente-apellido').value,
            nombre: document.getElementById('paciente-nombre').value,
            dni: document.getElementById('paciente-dni').value,
            fechaIngreso: document.getElementById('paciente-fechaIngreso').value,
            domicilio: {
                calle: document.getElementById('paciente-calle').value,
                numero: document.getElementById('paciente-numero').value,
                localidad: document.getElementById('paciente-localidad').value,
                provincia: document.getElementById('paciente-provincia').value
            }
        };
        fetchAPI(`${API_BASE_URL}/paciente/guardar`, 'POST', nuevoPaciente)
            .then(data => {
                console.log(data);
                alert("Paciente agregado con éxito");
                this.reset();
                loadPacientes();
            })
            .catch(error => console.error('Error:', error));
    });

    document.getElementById('editar-paciente-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const pacienteModificado = {
            id: document.getElementById('editar-paciente-id').value,
            apellido: document.getElementById('editar-paciente-apellido').value,
            nombre: document.getElementById('editar-paciente-nombre').value,
            dni: document.getElementById('editar-paciente-dni').value,
            fechaIngreso: document.getElementById('editar-paciente-fechaIngreso').value,
            domicilio: {
                id: document.getElementById('editar-domicilio-paciente-id').value,
                calle: document.getElementById('editar-paciente-calle').value,
                numero: document.getElementById('editar-paciente-numero').value,
                localidad: document.getElementById('editar-paciente-localidad').value,
                provincia: document.getElementById('editar-paciente-provincia').value
            }
        };
        fetchAPI(`${API_BASE_URL}/paciente/modificar`, 'PUT', pacienteModificado)
            .then(message => {
                console.log(message);
                const editarModalPaciente = bootstrap.Modal.getInstance(document.getElementById('editarModalPaciente'));
                editarModalPaciente.hide();
                alert("Paciente modificado con éxito");
                loadPacientes();
            })
            .catch(error => console.error('Error:', error));
    });

    // Turno
    document.getElementById('turno-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const nuevoTurno = {
            paciente_id: document.getElementById('turno-paciente').value,
            odontologo_id: document.getElementById('turno-odontologo').value,
            fecha: document.getElementById('turno-fecha').value
        };
        fetchAPI(`${API_BASE_URL}/turnos/guardar`, 'POST', nuevoTurno)
            .then(data => {
                console.log(data);
                alert("Turno agregado con éxito");
                this.reset();
                loadTurnos();
            })
            .catch(error => console.error('Error:', error));
    });



}
