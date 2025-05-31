<#import "layout.ftlh" as layout>
<@layout.layout ; spring>

    <div class="container-fluid bg-light py-4 mb-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <h3 class="mb-4 text-primary">Create New Flight</h3>

                <div class="card shadow-sm">
                    <div class="card-body p-4">
                        <form id="flightForm" action="/company/create" method="post" class="row g-3" onsubmit="return validateFlightForm()">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                            <div class="col-md-6">
                                <label for="departureCity" class="form-label fw-bold">Departure City</label>
                                <@spring.formSingleSelect "flightForm.departureCity", cityOptions, 'id="departureCity" class="form-select" required' />
                                <@spring.showErrors "<br>", "text-danger small" />
                            </div>
                            <div class="col-md-6">
                                <label for="arrivalCity" class="form-label fw-bold">Arrival City</label>
                                <@spring.formSingleSelect "flightForm.arrivalCity", cityOptions, 'id="arrivalCity" class="form-select" required' />
                                <@spring.showErrors "<br>", "text-danger small" />
                            </div>
                            <div class="col-md-6">
                                <label for="departureTime" class="form-label fw-bold">Departure Date & Time</label>
                                <@spring.formInput "flightForm.departureTime", 'id="departureTime" class="form-control flatpickr" required', "text" />
                                <@spring.showErrors "<br>", "text-danger small" />
                            </div>
                            <div class="col-md-6">
                                <label for="arrivalTime" class="form-label fw-bold">Arrival Date & Time</label>
                                <@spring.formInput "flightForm.arrivalTime", 'id="arrivalTime" class="form-control flatpickr" required', "text" />
                                <@spring.showErrors "<br>", "text-danger small" />
                            </div>
                            <div class="col-12 text-end">
                                <a href="/company" class="btn btn-secondary me-2">Cancel</a>
                                <button type="submit" class="btn btn-primary px-4">Create Flight</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

    <script>
        // Initialize Flatpickr for date-time pickers
        flatpickr(".flatpickr", {
            enableTime: true,
            dateFormat: "Y-m-d\\TH:i", // Format compatible with datetime-local
            time_24hr: true,
            minDate: "today" // Prevents selecting past dates
        });

        function validateFlightForm() {
            const departureCity = document.querySelector('#departureCity').value;
            const arrivalCity = document.querySelector('#arrivalCity').value;
            const departureTime = document.querySelector('#departureTime').value;
            const arrivalTime = document.querySelector('#arrivalTime').value;

            if (!departureCity || !arrivalCity) {
                alert("Please select both departure and arrival cities.");
                return false;
            }
            if (departureCity === arrivalCity) {
                alert("Departure and arrival cities cannot be the same.");
                return false;
            }
            if (!departureTime || !arrivalTime) {
                alert("Please select both departure and arrival dates.");
                return false;
            }
            const depTime = new Date(departureTime);
            const arrTime = new Date(arrivalTime);
            const now = new Date();

            if (depTime <= now) {
                alert("Departure time must be in the future.");
                return false;
            }
            if (arrTime <= now) {
                alert("Arrival time must be in the future.");
                return false;
            }
            if (depTime >= arrTime) {
                alert("Arrival time must be after departure time.");
                return false;
            }
            return true;
        }
    </script>
</@layout.layout>