<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>

    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign
        userPr = SPRING_SECURITY_CONTEXT.authentication.principal
        name = userPr.getUsername()
        isAdmin = userPr.authorities?seq_contains("ROLE_ADMIN")
        isCompany = userPr.authorities?seq_contains("ROLE_COMPANY")
        isUser = userPr.authorities?seq_contains("ROLE_USER")
        >
    </#if>

    <div class="container-fluid bg-light py-4 mb-5">
        <div class="row justify-content-center">
            <div class="col-md-10 col-lg-8">
                <form id="searchForm" action="/flights/search" method="get" class="row g-3 bg-white p-4 rounded shadow" onsubmit="return validateForm()">
                    <!-- Departure City -->
                    <div class="col-md-3">
                        <label for="departureCity" class="form-label fw-bold">Departure City</label>
                        <@spring.formSingleSelect "searchDto.departureCity", cityOptions, 'id="departureCity" class="form-select" required' />
                        <#if spring.status.errorMessages?has_content>
                            <div class="text-danger mt-1 small">
                                <@spring.showErrors "<br>", "error text-danger" />
                            </div>
                        </#if>
                    </div>

                    <!-- Swap and Clear Buttons -->
                    <div class="col-md-2 d-flex align-items-end pb-2">
                        <button type="button" class="btn btn-outline-secondary me-2" id="swapCities" title="Swap cities">
                            <i class="bi bi-arrow-left-right"></i>
                        </button>
                        <button type="button" class="btn btn-outline-danger" id="clearCities" title="Clear cities">
                            <i class="bi bi-x"></i>
                        </button>
                    </div>

                    <!-- Arrival City -->
                    <div class="col-md-3">
                        <label for="arrivalCity" class="form-label fw-bold">Arrival City</label>
                        <@spring.formSingleSelect "searchDto.arrivalCity", cityOptions, 'id="arrivalCity" class="form-select" required' />
                        <#if spring.status.errorMessages?has_content>
                            <div class="text-danger mt-1 small">
                                <@spring.showErrors "<br>", "error text-danger" />
                            </div>
                        </#if>
                    </div>

                    <!-- Date Range -->
                    <div class="col-md-4">
                        <label for="dateRange" class="form-label fw-bold">Travel Dates</label>
                        <input type="text" id="dateRange" name="dateRange" class="form-control" placeholder="Select dates" required>
                        <input type="hidden" name="departureDate" id="departureDate">
                        <input type="hidden" name="returnDate" id="returnDate">
                        <#if departureDateError??>
                            <div class="text-danger mt-1 small">${departureDateError}</div>
                        </#if>
                        <#if returnDateError??>
                            <div class="text-danger mt-1 small">${returnDateError}</div>
                        </#if>
                    </div>

                    <!-- Submit Button -->
                    <div class="col-12 text-end">
                        <button type="submit" class="btn btn-warning text-white px-4">Search Flights</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-md-10 col-lg-8">
                <div id="flightResults" data-is-user="${(known && isUser)?c}">
                    <#if spring.status?? && spring.status.error>
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <strong>Please fix the following errors:</strong>
                            <ul class="mb-0">
                                <#list spring.status.errorMessages as error>
                                    <li>${error}</li>
                                </#list>
                            </ul>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    <#elseif flights?? && flights?has_content>
                        <h3 class="mb-4 text-primary">Available Flights</h3>
                        <#list flights as flight>
                            <div class="card shadow-sm mb-3">
                                <div class="card-body p-4">
                                    <div class="row align-items-center">
                                        <div class="col-md-2">
                                            <#if flight.company.logoPath??>
                                                <img src="${flight.company.logoPath}" alt="${flight.company.name} Logo" class="img-fluid" style="max-height: 50px;" />
                                            <#else>
                                                <span class="fw-bold">${flight.company.name}</span>
                                            </#if>
                                        </div>
                                        <div class="col-md-3">
                                            <p class="mb-1 fw-bold">${cityOptions[flight.departureCity]!flight.departureCity} → ${cityOptions[flight.arrivalCity]!flight.arrivalCity}</p>
                                            <p class="mb-0 text-muted">Flight ${flight.flightNumber}</p>
                                        </div>
                                        <div class="col-md-3">
                                            <p class="mb-1">Depart: ${flight.departureTime.toLocalDate()}</p>
                                            <p class="mb-0">Arrive: ${flight.arrivalTime.toLocalDate()}</p>
                                        </div>
                                        <div class="col-md-2">
                                            <p class="mb-0 fw-bold">From ${flight.tickets[0].price} USD</p>
                                        </div>
                                        <div class="col-md-2 text-end">
                                            <a href="${(known && isUser)?then('/booking?ticketId=${flight.tickets[0].id}', '/auth/login')}" class="btn btn-primary btn-sm px-3">Book Now</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>

                        <#if totalPages?? && totalPages gt 1>
                            <nav aria-label="Page navigation" class="mt-4">
                                <ul class="pagination justify-content-center">
                                    <#if currentPage gt 1>
                                        <li class="page-item">
                                            <a class="page-link" href="/flights/search?page=${currentPage - 1}&departureCity=${searchDto.departureCity!''}&arrivalCity=${searchDto.arrivalCity!''}&departureDate=${searchDto.departureDate!''}&returnDate=${searchDto.returnDate!''}">Previous</a>
                                        </li>
                                    </#if>
                                    <#list 1..totalPages as page>
                                        <li class="page-item <#if page == currentPage>active</#if>">
                                            <a class="page-link" href="/flights/search?page=${page}&departureCity=${searchDto.departureCity!''}&arrivalCity=${searchDto.arrivalCity!''}&departureDate=${searchDto.departureDate!''}&returnDate=${searchDto.returnDate!''}">${page}</a>
                                        </li>
                                    </#list>
                                    <#if currentPage lt totalPages>
                                        <li class="page-item">
                                            <a class="page-link" href="/flights/search?page=${currentPage + 1}&departureCity=${searchDto.departureCity!''}&arrivalCity=${searchDto.arrivalCity!''}&departureDate=${searchDto.departureDate!''}&returnDate=${searchDto.returnDate!''}">Next</a>
                                        </li>
                                    </#if>
                                </ul>
                            </nav>
                        </#if>
                    <#elseif flights??>
                        <div class="alert alert-info text-center" role="alert">
                            No flights found for the selected criteria. Try adjusting your search parameters.
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            if (typeof flatpickr === 'undefined') {
                console.error('Flatpickr is not loaded. Please ensure the Flatpickr library is included.');
                return;
            }

            try {
                const dateRangePicker = flatpickr("#dateRange", {
                    mode: "range",
                    minDate: "today",
                    dateFormat: "Y-m-d",
                    allowInvalidPreload: false,
                    onClose: function(selectedDates, dateStr, instance) {
                        if (selectedDates.length !== 2) {
                            instance.clear();
                            document.querySelector('#departureDate').value = '';
                            document.querySelector('#returnDate').value = '';
                            alert("Please select both departure and return dates.");
                            return;
                        }
                        document.querySelector('#departureDate').value = selectedDates[0].toISOString().split('T')[0];
                        document.querySelector('#returnDate').value = selectedDates[1].toISOString().split('T')[0];
                    }
                });
            } catch (error) {
                console.error('Error initializing Flatpickr:', error);
                alert('Failed to initialize date picker. Please try again.');
            }

            function validateForm() {
                const departureDate = document.querySelector('#departureDate').value;
                const returnDate = document.querySelector('#returnDate').value;
                const departureCity = document.querySelector('#departureCity').value;
                const arrivalCity = document.querySelector('#arrivalCity').value;

                if (!departureCity || !arrivalCity) {
                    alert("Please select both departure and arrival cities.");
                    return false;
                }
                if (!departureDate || !returnDate) {
                    alert("Please select both departure and return dates.");
                    return false;
                }
                if (departureCity === arrivalCity) {
                    alert("Departure and arrival cities cannot be the same.");
                    return false;
                }
                return true;
            }

            document.querySelector('#swapCities').addEventListener('click', () => {
                const departure = document.querySelector('#departureCity');
                const arrival = document.querySelector('#arrivalCity');
                const temp = departure.value;
                departure.value = arrival.value;
                arrival.value = temp;
            });

            document.querySelector('#clearCities').addEventListener('click', () => {
                document.querySelector('#departureCity').value = '';
                document.querySelector('#arrivalCity').value = '';
                document.querySelector('#dateRange').value = '';
                document.querySelector('#departureDate').value = '';
                document.querySelector('#returnDate').value = '';
            });
        });
    </script>
</@layout.layout>