/**
 * =====================================================
 * Student Management System - JavaScript
 * =====================================================
 * Frontend JavaScript for Student Management Application
 */

// Logging utility
const Logger = {
    log: function(msg) {
        console.log('[LOG]', msg);
    },
    error: function(msg) {
        console.error('[ERROR]', msg);
    },
    warn: function(msg) {
        console.warn('[WARN]', msg);
    },
    info: function(msg) {
        console.info('[INFO]', msg);
    }
};

/**
 * Document Ready - Initialize on page load
 */
document.addEventListener('DOMContentLoaded', function() {
    Logger.log('Application initialized');
    
    // Initialize tooltips
    initializeTooltips();
    
    // Initialize popovers
    initializePopovers();
    
    // Setup auto-hide alerts
    setupAutoHideAlerts();
    
    // Setup form handlers
    setupFormHandlers();
    
    // Setup button handlers
    setupButtonHandlers();
});

/**
 * Initialize Bootstrap Tooltips
 */
function initializeTooltips() {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    Logger.log('Tooltips initialized');
}

/**
 * Initialize Bootstrap Popovers
 */
function initializePopovers() {
    const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
    Logger.log('Popovers initialized');
}

/**
 * Auto-hide alerts after 5 seconds
 */
function setupAutoHideAlerts() {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            const alertInstance = new bootstrap.Alert(alert);
            alertInstance.close();
        }, 5000);
    });
    Logger.log('Alert auto-hide setup complete');
}

/**
 * Setup Form Handlers
 */
function setupFormHandlers() {
    // Form validation
    const forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function(form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
    Logger.log('Form handlers setup complete');
}

/**
 * Setup Button Handlers
 */
function setupButtonHandlers() {
    // Search button
    const searchBtn = document.querySelector('button[type="submit"]:contains("Search")');
    if (searchBtn) {
        searchBtn.addEventListener('click', function(e) {
            Logger.log('Search button clicked');
        });
    }
    
    // Delete confirmation
    const deleteButtons = document.querySelectorAll('a[href*="/delete/"]');
    deleteButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            if (!confirm('Are you sure you want to delete this student? This action cannot be undone.')) {
                e.preventDefault();
            }
        });
    });
    Logger.log('Button handlers setup complete');
}

/**
 * API Call Function - Generic
 * @param {string} url - API endpoint URL
 * @param {string} method - HTTP method (GET, POST, PUT, DELETE)
 * @param {object} data - Optional request data
 * @returns {Promise} - Promise resolving to response data
 */
function apiCall(url, method = 'GET', data = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        }
    };

    if (data && (method === 'POST' || method === 'PUT')) {
        options.body = JSON.stringify(data);
    }

    return fetch(url, options)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .catch(error => {
            Logger.error('API Call Error: ' + error);
            throw error;
        });
}

/**
 * Get All Students API
 */
function getAllStudents() {
    Logger.log('Fetching all students');
    return apiCall('/students/api/all', 'GET');
}

/**
 * Get Student by ID API
 */
function getStudentById(id) {
    Logger.log('Fetching student with ID: ' + id);
    return apiCall('/students/api/' + id, 'GET');
}

/**
 * Create New Student API
 */
function createStudent(studentData) {
    Logger.log('Creating new student');
    return apiCall('/students/api/create', 'POST', studentData);
}

/**
 * Update Student API
 */
function updateStudent(id, studentData) {
    Logger.log('Updating student with ID: ' + id);
    return apiCall('/students/api/update/' + id, 'PUT', studentData);
}

/**
 * Delete Student API
 */
function deleteStudent(id) {
    Logger.log('Deleting student with ID: ' + id);
    return apiCall('/students/api/delete/' + id, 'DELETE');
}

/**
 * Show Loading Spinner
 */
function showLoader() {
    const loader = document.getElementById('loader');
    if (loader) {
        loader.style.display = 'block';
    }
}

/**
 * Hide Loading Spinner
 */
function hideLoader() {
    const loader = document.getElementById('loader');
    if (loader) {
        loader.style.display = 'none';
    }
}

/**
 * Show Toast Notification
 */
function showToast(message, type = 'info') {
    const toastContainer = document.getElementById('toastContainer');
    if (!toastContainer) {
        Logger.warn('Toast container not found');
        alert(message);
        return;
    }

    const toastId = 'toast-' + Date.now();
    const toastHtml = `
        <div id="${toastId}" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header bg-${type} text-white">
                <strong class="me-auto">Notification</strong>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast"></button>
            </div>
            <div class="toast-body">
                ${message}
            </div>
        </div>
    `;

    toastContainer.insertAdjacentHTML('beforeend', toastHtml);
    const toast = new bootstrap.Toast(document.getElementById(toastId));
    toast.show();

    // Remove toast element after hidden
    document.getElementById(toastId).addEventListener('hidden.bs.toast', function() {
        this.remove();
    });
}

/**
 * Format Date
 */
function formatDate(dateString) {
    const date = new Date(dateString);
    const options = { 
        year: 'numeric', 
        month: 'short', 
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    };
    return date.toLocaleDateString('en-US', options);
}

/**
 * Validate Email
 */
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

/**
 * Validate Form Field
 */
function validateField(fieldId) {
    const field = document.getElementById(fieldId);
    if (!field) return true;

    if (field.value.trim() === '') {
        field.classList.add('is-invalid');
        return false;
    } else {
        field.classList.remove('is-invalid');
        return true;
    }
}

/**
 * Highlight Table Row
 */
function highlightRow(rowElement) {
    // Remove previous highlight
    document.querySelectorAll('table tbody tr').forEach(row => {
        row.classList.remove('table-active');
    });

    // Add highlight to current row
    if (rowElement) {
        rowElement.classList.add('table-active');
    }
}

/**
 * Export Table to CSV
 */
function exportTableToCSV(filename = 'students.csv') {
    const table = document.querySelector('table');
    if (!table) {
        Logger.warn('No table found for export');
        return;
    }

    let csv = [];
    const rows = table.querySelectorAll('tr');

    rows.forEach(row => {
        let rowData = [];
        const cells = row.querySelectorAll('td, th');
        cells.forEach(cell => {
            rowData.push('"' + cell.textContent.trim().replace(/"/g, '""') + '"');
        });
        csv.push(rowData.join(','));
    });

    const csvContent = 'data:text/csv;charset=utf-8,' + encodeURIComponent(csv.join('\n'));
    const link = document.createElement('a');
    link.setAttribute('href', csvContent);
    link.setAttribute('download', filename);
    link.click();

    Logger.log('Table exported to CSV: ' + filename);
}

/**
 * Print Page
 */
function printPage() {
    window.print();
    Logger.log('Page print initiated');
}

/**
 * Debounce Function - Useful for search
 */
function debounce(func, delay) {
    let timeoutId;
    return function(...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => {
            func(...args);
        }, delay);
    };
}

/**
 * Search Students (Debounced)
 */
const debouncedSearch = debounce(function(searchTerm) {
    if (searchTerm.length > 2) {
        Logger.log('Searching for: ' + searchTerm);
        // Perform search
    }
}, 300);

/**
 * Setup Search Input Listener
 */
function setupSearchListener() {
    const searchInput = document.querySelector('input[name="search"]');
    if (searchInput) {
        searchInput.addEventListener('input', function(e) {
            debouncedSearch(e.target.value);
        });
    }
}

/**
 * Confirm Action
 */
function confirmAction(message = 'Are you sure?') {
    return confirm(message);
}

/**
 * Redirect to URL
 */
function redirectTo(url) {
    Logger.log('Redirecting to: ' + url);
    window.location.href = url;
}

/**
 * Get URL Parameter
 */
function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

/**
 * Highlight Navigation Link
 */
function highlightNavLink() {
    const currentLocation = location.pathname;
    const navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    
    navLinks.forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('href') === currentLocation) {
            link.classList.add('active');
        }
    });
}

/**
 * Setup keyboard shortcuts
 */
function setupKeyboardShortcuts() {
    document.addEventListener('keydown', function(event) {
        // Ctrl+F for search focus
        if (event.ctrlKey && event.key === 'f') {
            event.preventDefault();
            const searchInput = document.querySelector('input[name="search"]');
            if (searchInput) {
                searchInput.focus();
            }
        }

        // Ctrl+K for add new
        if (event.ctrlKey && event.key === 'k') {
            event.preventDefault();
            redirectTo('/students/add');
        }
    });

    Logger.log('Keyboard shortcuts initialized');
}

/**
 * Setup Dark Mode Toggle (Optional)
 */
function setupDarkMode() {
    const darkModeToggle = document.getElementById('darkModeToggle');
    if (darkModeToggle) {
        const isDarkMode = localStorage.getItem('darkMode') === 'true';
        
        if (isDarkMode) {
            document.body.classList.add('dark-mode');
            darkModeToggle.checked = true;
        }

        darkModeToggle.addEventListener('change', function() {
            document.body.classList.toggle('dark-mode');
            localStorage.setItem('darkMode', document.body.classList.contains('dark-mode'));
        });
    }
}

/**
 * Initialize Page on Load
 */
window.addEventListener('load', function() {
    highlightNavLink();
    setupSearchListener();
    setupKeyboardShortcuts();
    setupDarkMode();
    Logger.log('Page fully loaded and initialized');
});

/**
 * Handle Page Visibility (Optional)
 */
document.addEventListener('visibilitychange', function() {
    if (document.hidden) {
        Logger.log('Page hidden');
    } else {
        Logger.log('Page visible');
    }
});

// Export functions for global use
window.StudentApp = {
    getAllStudents,
    getStudentById,
    createStudent,
    updateStudent,
    deleteStudent,
    showToast,
    showLoader,
    hideLoader,
    formatDate,
    isValidEmail,
    validateField,
    highlightRow,
    exportTableToCSV,
    printPage,
    confirmAction,
    redirectTo,
    getUrlParameter,
    Logger
};
