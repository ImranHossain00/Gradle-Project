<%@include file="includes/header.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container col-lg-6">
    <h2 class="text-info">Sign up</h2>
    <hr class="md-4">

    <form class="form-horizontal"
          action="<c:url value="signup"/>"
          method="post"
          role="form">
        <div class="form-group">
            <label for="firstName">First Name</label>
            <input type="text"
                   class="form-control"
                   name="firstName"
                   value="${dto.firstName}"
                   id="firstName">
            <c:if test="${errors.firstName != null}">
                <small class="text-danger">${errors.firstName}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name</label>
            <input type="text"
                   class="form-control"
                   name="lastName"
                   value="${dto.lastName}"
                   id="lastName">
            <c:if test="${errors.lastName != null}">
                <small class="text-danger">${errors.lastName}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email"
                   class="form-control"
                   name="email"
                   value="${dto.email}"
                   id="email"
                   placeholder="example@domain.com">
            <c:if test="${errors.email != null}">
                <small class="text-danger">${errors.email}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password"
                   class="form-control"
                   name="password"
                   value="${dto.password}"
                   id="password">
            <c:if test="${errors.password != null}">
                <small class="text-danger">${errors.password}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password"
                   class="form-control"
                   name="confirmPassword"
                   value="${dto.confirmPassword}"
                   id="confirmPassword">
            <c:if test="${errors.confirmPassword != null}">
                <small class="text-danger">${errors.confirmPassword}</small>
            </c:if>
        </div>
        <hr class="mb-4">
        <div class="form-group">
            <button class="btn btn-lg btn-success"
                    type="submit">
                Sign up
            </button>
        </div>
    </form>
</div>
<%@include file="includes/footer.jsp"%>