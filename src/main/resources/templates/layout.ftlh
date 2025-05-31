<#import "/spring.ftl" as spring>
<#macro layout>

    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${title!"Flight Booking"}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
              crossorigin="anonymous">
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #f4f4f9;
            }

            .navbar-brand {
                font-weight: bold;
                color: #007bff !important;
            }

            .navbar {
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            .main-content {
                min-height: calc(100vh - 200px);
                padding: 40px 0;
            }

            .footer {
                background-color: #343a40;
                color: white;
                padding: 20px 0;
                text-align: center;
            }

            .footer a {
                color: #ffffff;
                text-decoration: none;
            }

            .footer a:hover {
                text-decoration: underline;
            }

            html, body {
                height: 100%;
                margin: 0;
                display: flex;
                flex-direction: column;
            }

            .main-content {
                flex: 1 0 auto;
            }

            .footer {
                flex-shrink: 0;
            }
        </style>
    </head>
    <body>
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
    <header>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand" href="/flights">FlightBooking</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="/flights">Flights</a>
                        </li>
                        <#if !known>
                            <li class="nav-item">
                                <a class="nav-link" href="/auth/login">Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/auth/register">Register</a>
                            </li>
                        <#else>
                            <li class="nav-item">
                                <#if isCompany>
                                    <a class="nav-link" href="/company">Company Profile</a>
                                <#else>
                                    <a class="nav-link" href="/profile">Profile</a>
                                </#if>
                            </li>
                            <#if isAdmin>
                                <li class="nav-item">
                                    <a class="nav-link" href="/admin">Admin Panel</a>
                                </li>
                            </#if>
                            <li class="nav-item">
                                <form action="/logout" method="post" class="d-inline">
                                    <#if _csrf??>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </#if>
                                    <button type="submit" class="nav-link btn btn-link">Logout</button>
                                </form>
                            </li>
                        </#if>
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <main class="main-content">
        <div class="container">
            <#nested spring>
        </div>
    </main>

    <footer class="footer">
        <div class="container">
            <p>© 2025 FlightBooking. All rights reserved.</p>
            <p><a href="/about">About Us</a> | <a href="/contact">Contact</a></p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/doist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>