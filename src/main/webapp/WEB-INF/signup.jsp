<%--
  Created by IntelliJ IDEA.
  User: peripatetic
  Date: 10/17/23
  Time: 11:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/headr.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container" >
    <br/>
    
    <h2 class="h2">Sign Up</h2>
    <hr class="mb-4">
    
    <form class="form-horizontal" role="form"
          action="<c:url value="/signup"/>" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username"
                   value="${userDto.username}"
                   name="username" placeholder=""/>
            <jsp:useBean id="errors" scope="request" type="com.bazlur.eshoppers.domain.User"/>
            <c:if test="${errors.username != null}">
                <small class="text-danger">${errors.username}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email"
                   value="${userDto.email}"
                   name="email" placeholder="example@mail.com"/>
            <c:if test="${errors.email != null}">
                <small class="text-danger">${errors.email}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password"
                   name="password"/>
            <c:if test="${errors.password != null}">
                <small class="text-danger">${errors.password}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="passwordConfirmed">Password Confirmed</label>
            <input type="password" class="form-control" id="passwordConfirmed"
                   name="passwordConfirmed"/>
            <c:if test="${errors.passwordConfirmed != null}">
                <small class="text-danger">${errors.passwordConfirmed}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="email">First Name</label>
            <input type="text" class="form-control" id="firstName"
                   value="${userDto.firstName}"
                   name="firstName" placeholder="Ex. Imran"/>
            <c:if test="${errors.firstName != null}">
                <small class="text-danger">${errors.firstName}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="email">Last Name</label>
            <input type="text" class="form-control" id="lastName"
                   value="${userDto.lastName}"
                   name="lastName" placeholder="Ex. Hossain"/>
            <c:if test="${errors.lastName != null}">
                <small class="text-danger">${errors.lastName}</small>
            </c:if>
        </div>
        <hr class="mb-4">
        <div class="form-group">
            <button class="btn btn-primary btn-lg" type="submit"
                    onclick="return validatePassword()">
                Signup
            </button>
        </div>
    </form>
</div>
<%@include file="includes/footer.jsp"%>
<%--<script type="text/javascript">--%>
<%--    function validatePassword() {--%>
<%--        var password--%>
<%--            = document.getElementById("password").value;--%>
<%--        var confirmPassword--%>
<%--            = document.getElementById("passwordConfirmed").value;--%>
<%--        if (password !== confirmPassword) {--%>
<%--            alert("Passwords do not match");--%>
<%--            return false;--%>
<%--        }--%>
<%--        return true;--%>
<%--    }--%>
<%--</script>--%>
