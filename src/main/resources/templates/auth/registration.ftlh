<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h2 class="card-title text-center mb-4">Register</h2>
                    <form action="/auth/register" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </#if>
                        <div class="form-group mb-3">
                            <label class="form-label" for="form3Example1cg">Email</label>
                            <@spring.formInput "userDto.email" 'id="form3Example1cg" class="form-control" type="email"' />
                            <@spring.showErrors "<br>" "error text-danger" />
                        </div>
                        <div class="form-group mb-3">
                            <label class="form-label" for="form3Example2cg">Full Name</label>
                            <@spring.formInput "userDto.name" 'id="form3Example2cg" class="form-control"' />
                            <@spring.showErrors "<br>" "error text-danger" />
                        </div>
                        <div class="form-group mb-3">
                            <label class="form-label" for="form3Example3cg">Password</label>
                            <@spring.formInput "userDto.password" 'id="form3Example3cg" class="form-control" type="password"' />
                            <@spring.showErrors "<br>" "error text-danger" />
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Register</button>
                    </form>
                    <p class="text-center mt-3">
                        Already have an account? <a href="/auth/login">Login</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>