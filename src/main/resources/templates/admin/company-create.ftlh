<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>
    <div class="container-fluid bg-light py-4 mb-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <h3 class="mb-4 text-primary">Create New Company</h3>

                <div class="card shadow-sm">
                    <div class="card-body p-4">
                        <#if error??>
                            <div class="alert alert-danger" role="alert">${error}</div>
                        </#if>
                        <form action="/admin/company/create" method="post" class="row g-3">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <div class="col-md-6">
                                <label for="name" class="form-label fw-bold">Company Name</label>
                                <@spring.formInput "companyForm.name", 'id="name" class="form-control" required', "text" />
                                <@spring.showErrors "<br>", "text-danger small" />
                            </div>
                            <div class="col-md-6">
                                <label for="email" class="form-label fw-bold">Email</label>
                                <@spring.formInput "companyForm.email", 'id="email" class="form-control" type="email" required', "text" />
                                <@spring.showErrors "<br>", "text-danger small" />
                            </div>
                            <div class="col-md-6">
                                <label for="password" class="form-label fw-bold">Password</label>
                                <@spring.formInput "companyForm.password", 'id="password" class="form-control" type="password" required', "text" />
                                <@spring.showErrors "<br>", "text-danger small" />
                            </div>
                            <div class="col-12 text-end">
                                <a href="/admin" class="btn btn-secondary me-2">Cancel</a>
                                <button type="submit" class="btn btn-primary px-4">Create Company</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>