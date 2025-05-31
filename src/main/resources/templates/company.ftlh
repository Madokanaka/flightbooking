<#import "layout.ftlh" as layout>
<@layout.layout ; spring>

    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign
        username = SPRING_SECURITY_CONTEXT.authentication.getName()
        isCompany = SPRING_SECURITY_CONTEXT.authentication.authorities?seq_contains("ROLE_COMPANY")
        >
    </#if>

    <#assign defaultLogo = "https://static.vecteezy.com/system/resources/thumbnails/000/439/863/small_2x/Basic_Ui__28186_29.jpg">

    <div class="container-fluid bg-light py-4 mb-5">
        <div class="row justify-content-center">
            <div class="col-md-10 col-lg-8">
                <h3 class="mb-4 text-primary">Company Profile</h3>

                <!-- Company Details -->
                <div class="card shadow-sm mb-4">
                    <div class="card-body p-4">
                        <h4 class="mb-3">Company Details</h4>
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Company Name</label>
                                <p>${company.name!''}</p>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Email</label>
                                <p>${company.email}</p>
                            </div>
                            <div class="col-md-12">
                                <label class="form-label fw-bold">Logo</label>
                                <img src="${company.logoPath?has_content?then('/api/images/' + company.logoPath, defaultLogo)}" alt="Company Logo" class="img-fluid" style="max-height: 100px;" />
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Create Flight Link -->
                <div class="mb-4 text-end">
                    <a href="/company/create" class="btn btn-primary px-4">Create New Flight</a>
                </div>

                <!-- Flight List -->
                <div class="card shadow-sm">
                    <div class="card-body p-4">
                        <h4 class="mb-3">Your Flights</h4>
                        <#if flights?? && flights?has_content>
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Flight Number</th>
                                    <th>Departure City</th>
                                    <th>Arrival City</th>
                                    <th>Departure Date</th>
                                    <th>Arrival Date</th>
                                    <th>Tickets</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list flights as flight>
                                    <tr>
                                        <td>${flight.flightNumber}</td>
                                        <td>${cityOptions[flight.departureCity]!flight.departureCity}</td>
                                        <td>${cityOptions[flight.arrivalCity]!flight.arrivalCity}</td>
                                        <td>${flight.departureTime.toLocalDate()}</td>
                                        <td>${flight.arrivalTime.toLocalDate()}</td>
                                        <td>
                                            <button class="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#ticketModal${flight.id}">
                                                View Tickets
                                            </button>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>

                            <!-- Pagination -->
                            <#if totalPages?? && totalPages gt 1>
                                <nav aria-label="Page navigation" class="mt-4">
                                    <ul class="pagination justify-content-center">
                                        <#if currentPage gt 1>
                                            <li class="page-item">
                                                <a class="page-link" href="/company?page=${currentPage - 1}">Previous</a>
                                            </li>
                                        </#if>
                                        <#list 1..totalPages as page>
                                            <li class="page-item <#if page == currentPage>active</#if>">
                                                <a class="page-link" href="/company?page=${page}">${page}</a>
                                            </li>
                                        </#list>
                                        <#if currentPage lt totalPages>
                                            <li class="page-item">
                                                <a class="page-link" href="/company?page=${currentPage + 1}">Next</a>
                                            </li>
                                        </#if>
                                    </ul>
                                </nav>
                            </#if>
                        <#else>
                            <div class="alert alert-info text-center" role="alert">
                                No flights found.
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Ticket Modals -->
    <#list flights as flight>
        <div class="modal fade" id="ticketModal${flight.id}" tabindex="-1" aria-labelledby="ticketModalLabel${flight.id}" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="ticketModalLabel${flight.id}">Tickets for Flight ${flight.flightNumber}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Seat Number</th>
                                <th>Class</th>
                                <th>Price</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list flight.tickets as ticket>
                                <tr>
                                    <td>${ticket.seatNumber}</td>
                                    <td>${ticket.ticketClass}</td>
                                    <td>${ticket.price} USD</td>
                                    <td>${ticket.isBooked()?then("Booked", "Available")}</td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </#list>

    <!-- Bootstrap and jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</@layout.layout>