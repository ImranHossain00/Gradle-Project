<%@include file="includes/header.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container col-lg-6">
    <h2 class="text-info">Log In</h2>
    <hr class="mb-4">
    <form class="form-horizontal"
          action="<c:url value="/login"/>"
          method="post"
          role="form">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email"
                   class="form-control"
                   name="email"
                   value="${dto.email}"
                   id="email"/>
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
                   id="password"/>
            <c:if test="${errors.password != null}">
                <small class="text-danger">${errors.password}</small>
            </c:if>
        </div>
        <hr class="mb-4">
        <div class="form-group">
            <div class="row">
                <div class="d-flex justify-content-start col-lg-6">
                    <button class="btn btn-success btn-lg"
                            type="submit">
                        Log In
                    </button>
                </div>
                <div class="d-flex justify-content-end col-lg-6">
                    <a class="btn btn-info btn-lg"
                       href="signup">
                        Create new
                    </a>
                </div>
            </div>
        </div>
    </form>
</div>
<%@include file="includes/footer.jsp"%>