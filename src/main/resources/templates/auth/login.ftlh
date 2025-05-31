<#import "../layout.ftlh" as layout>
<@layout.layout >
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h2 class="card-title text-center mb-4">Login</h2>
                    <#if error??>
                        <div class="alert alert-danger" role="alert">
                            Invalid email or password
                        </div>
                    </#if>
                    <form action="/auth/login" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </#if>
                        <div class="form-group mb-3">
                            <label class="form-label" for="form3Example1cg">Email</label>
                            <input type="email" name="username" id="form3Example1cg" class="form-control" required />
                        </div>
                        <div class="form-group mb-3">
                            <label class="form-label" for="form3Example2cg">Password</label>
                            <input type="password" name="password" id="form3Example2cg" class="form-control" required />
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Login</button>
                    </form>
                    <p class="text-center mt-3">
                        Don't have an account? <a href="/auth/register">Register</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>