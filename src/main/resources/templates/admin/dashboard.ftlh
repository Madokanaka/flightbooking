<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>

    <#assign defaultLogo = "https://static.vecteezy.com/system/resources/thumbnails/000/439/863/small_2x/Basic_Ui__28186_29.jpg">

    <div class="container-fluid bg-light py-4 mb-5">
        <div class="row justify-content-center">
            <div class="col-md-10 col-lg-8">
                <h3 class="mb-4 text-primary">Admin Dashboard</h3>

                <div class="card shadow-sm mb-4">
                    <div class="card-body p-4">
                        <a href="/admin/company/create" class="btn btn-primary mb-3">Create New Company</a>
                        <a href="/admin/flights" class="btn btn-secondary mb-3">View Flights</a>

                        <#if error??>
                            <div class="alert alert-danger" role="alert">${error}</div>
                        </#if>
                        <#if success??>
                            <div class="alert alert-success" role="alert">Operation successful!</div>
                        </#if>

                        <h4>Companies</h4>
                        <#if companies?? && companies?has_content>
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Logo</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list companies as company>
                                    <tr>
                                        <td>
                                            <img src="${company.logoPath?has_content?then('/api/images/' + company.logoPath, defaultLogo)}" alt="${company.name} Logo" class="company-logo" onerror="this.src='${defaultLogo}'" />
                                        </td>
                                        <td>${company.name}</td>
                                        <td>${company.email}</td>
                                        <td>${company.frozen?then("Frozen", "Active")}</td>
                                        <td>
                                            <form action="/admin/company/${company.id}/toggle-freeze" method="post">
                                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                                <button type="submit" class="btn btn-sm ${company.frozen?then('btn-success', 'btn-warning')}">
                                                    ${company.frozen?then("Unfreeze", "Freeze")}
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>

                            <!-- Pagination -->
                            <#if totalPages gt 1>
                                <nav aria-label="Page navigation" class="mt-4">
                                    <ul class="pagination justify-content-center">
                                        <li class="page-item <#if currentPage == 0>disabled</#if>">
                                            <a class="page-link" href="/admin?page=${currentPage - 1}" <#if currentPage == 0>tabindex="-1" aria-disabled="true"</#if>>Previous</a>
                                        </li>
                                        <#list 0..<totalPages as p>
                                            <li class="page-item <#if p == currentPage>active</#if>">
                                                <a class="page-link" href="/admin?page=${p}">${p + 1}</a>
                                            </li>
                                        </#list>
                                        <li class="page-item <#if currentPage == totalPages - 1>disabled</#if>">
                                            <a class="page-link" href="/admin?page=${currentPage + 1}" <#if currentPage == totalPages - 1>tabindex="-1" aria-disabled="true"</#if>>Next</a>
                                        </li>
                                    </ul>
                                </nav>
                                <p class="text-center text-muted">
                                    Showing page ${currentPage + 1} of ${totalPages} (${totalItems} total companies)
                                </p>
                            </#if>
                        <#else>
                            <p>No companies available.</p>
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
            margin: 0 auto;
        }
        @media (max-width: 768px) {
            .company-logo {
                max-height: 40px;
                max-width: 80px;
            }
        }
        table td {
            vertical-align: middle;
        }
    </style>
</@layout.layout>