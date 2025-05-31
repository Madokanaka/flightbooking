<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>
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
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list companies as company>
                                    <tr>
                                        <td>${company.name}</td>
                                        <td>${company.email}</td>
                                        <td>${company.isFrozen()?then("Booked", "Available")}</td>
                                        <td>
                                            <form action="/admin/company/${company.id}/toggle-freeze" method="post">
                                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                                <button type="submit" class="btn btn-sm ${company.isFrozen()?then('btn-success', 'btn-warning')}">
                                                    ${company.isFrozen()?then("Unfreeze", "Freeze")}
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        <#else>
                            <p>No companies available.</p>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>