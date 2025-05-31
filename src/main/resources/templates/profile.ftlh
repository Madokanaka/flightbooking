<#import "layout.ftlh" as layout>
<@layout.layout ; spring>

    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign
        userPr = SPRING_SECURITY_CONTEXT.authentication.principal
        name = userPr.getUsername()
        isUser = userPr.authorities?seq_contains("ROLE_USER")
        >
    </#if>

    <div class="container-fluid bg-light py-4 mb-5">
        <div class="row justify-content-center">
            <div class="col-md-10 col-lg-8">
                <h3 class="mb-4 text-primary">User Profile</h3>

                <!-- User Details -->
                <div class="card shadow-sm mb-4">
                    <div class="card-body p-4">
                        <h4 class="mb-3">Personal Details</h4>
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Full Name</label>
                                <p>${user.name!''}</p>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Email</label>
                                <p>${user.email!''}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Booking History -->
                <div class="card shadow-sm">
                    <div class="card-body p-4">
                        <h4 class="mb-3">Booking History</h4>
                        <#if bookings?? && bookings?has_content>
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Flight Number</th>
                                    <th>Departure City</th>
                                    <th>Arrival City</th>
                                    <th>Departure Date</th>
                                    <th>Seat Number</th>
                                    <th>Class</th>
                                    <th>Price</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list bookings as booking>
                                    <tr>
                                        <td>${booking.flightNumber}</td>
                                        <td>${cityOptions[booking.departureCity]!booking.departureCity}</td>
                                        <td>${cityOptions[booking.arrivalCity]!booking.arrivalCity}</td>
                                        <td>${booking.departureTime.toLocalDate()}</td>
                                        <td>${booking.seatNumber}</td>
                                        <td>${booking.ticketClass}</td>
                                        <td>${booking.price} USD</td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>

                            <!-- Pagination for Bookings -->
                            <#if totalPages?? && totalPages gt 1>
                                <nav aria-label="Page navigation" class="mt-4">
                                    <ul class="pagination justify-content-center">
                                        <#if currentPage gt 1>
                                            <li class="page-item">
                                                <a class="page-link" href="/profile?page=${currentPage - 1}">Previous</a>
                                            </li>
                                        </#if>
                                        <#list 1..totalPages as page>
                                            <li class="page-item <#if page == currentPage>active</#if>">
                                                <a class="page-link" href="/profile?page=${page}">${page}</a>
                                            </li>
                                        </#list>
                                        <#if currentPage lt totalPages>
                                            <li class="page-item">
                                                <a class="page-link" href="/profile?page=${currentPage + 1}">Next</a>
                                            </li>
                                        </#if>
                                    </ul>
                                </nav>
                            </#if>
                        <#else>
                            <div class="alert alert-info text-center" role="alert">
                                No bookings found.
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap and jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</@layout.layout>