<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>

    <#assign defaultLogo = "https://static.vecteezy.com/system/resources/thumbnails/000/439/863/small_2x/Basic_Ui__28186_29.jpg">

    <div class="container-fluid bg-light py-4 mb-5">
        <div class="row justify-content-center">
            <div class="col-md-10 col-lg-8">
                <h3 class="mb-4 text-primary">Company Flights and Bookings</h3>

                <div class="card shadow-sm">
                    <div class="card-body p-4">
                        <a href="/admin" class="btn btn-secondary mb-3">Back to Dashboard</a>

                        <#if companies?? && companies?has_content>
                            <#list companies as company>
                                <h4 class="d-flex align-items-center">
                                    <img src="${company.logoPath?has_content?then('/api/images/' + company.logoPath, defaultLogo)}" alt="${company.name} Logo" class="company-logo me-3" />
                                    ${company.name} (${company.email})
                                </h4>
                                <table class="table table-striped mb-5">
                                    <thead>
                                    <tr>
                                        <th>Flight Number</th>
                                        <th>Departure</th>
                                        <th>Arrival</th>
                                        <th>Seats</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list company.flights as flight>
                                        <tr>
                                            <td>${flight.flightNumber}</td>
                                            <td>${flight.departureCity} at ${flight.departureTime}</td>
                                            <td>${flight.arrivalCity} at ${flight.arrivalTime}</td>
                                            <td>
                                                <ul>
                                                    <#list flight.tickets as ticket>
                                                        <li>
                                                            Seat ${ticket.seatNumber} (${ticket.ticketClass}):
                                                            ${ticket.booked?then("Booked", "Available")}
                                                        </li>
                                                    </#list>
                                                </ul>
                                            </td>
                                        </tr>
                                    </#list>
                                    </tbody>
                                </table>
                            </#list>

                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-center">
                                    <li class="page-item <#if currentPage == 0>disabled</#if>">
                                        <a class="page-link" href="?page=${currentPage - 1}" <#if currentPage == 0>tabindex="-1" aria-disabled="true"</#if>>Previous</a>
                                    </li>
                                    <#list 0..<totalPages as p>
                                        <li class="page-item <#if p == currentPage>active</#if>">
                                            <a class="page-link" href="?page=${p}">${p + 1}</a>
                                        </li>
                                    </#list>
                                    <li class="page-item <#if currentPage == totalPages - 1>disabled</#if>">
                                        <a class="page-link" href="?page=${currentPage + 1}" <#if currentPage == totalPages - 1>tabindex="-1" aria-disabled="true"</#if>>Next</a>
                                    </li>
                                </ul>
                            </nav>
                            <p class="text-center text-muted">
                                Showing page ${currentPage + 1} of ${totalPages} (${totalItems} total companies)
                            </p>
                        <#else>
                            <p>No companies or flights available.</p>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <style>
        .company-logo {
            max-height: 50px;
            max-width: 100px;
            object-fit: contain;
            border-radius: 8px;
            border: 1px solid #ddd;
            padding: 2px;
            display: block;
        }
        @media (max-width: 768px) {
            .company-logo {
                max-height: 40px;
                max-width: 80px;
            }
        }
    </style>
</@layout.layout>