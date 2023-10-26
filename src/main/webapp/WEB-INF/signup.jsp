<%--
  Created by IntelliJ IDEA.
  User: peripatetic
<<<<<<< HEAD
  Date: 10/5/23
  Time: 8:34 PM
=======
  Date: 10/17/23
  Time: 11:26 PM
>>>>>>> 8bc685b (Adding as noob)
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/headr.jsp"%>
<%@include file="includes/navigation.jsp"%>

<<<<<<< HEAD
<div class="container">
    <br>

    <h2 class="h2">Sign Up</h2>
    <hr class="mb-4">

=======
<div class="container" >
    <br/>
    
    <h2 class="h2">Sign Up</h2>
    <hr class="mb-4">
    
>>>>>>> 8bc685b (Adding as noob)
    <form class="form-horizontal" role="form"
          action="<c:url value="/signup"/>" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username"
<<<<<<< HEAD
                   name="username" placeholder="" required minlength="4" maxlength="32"/>
            <c:if test="${errors.username != null}">
                <small class="text-danger"> ${errors.username}</small>
            </c:if>

        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="text" class="form-control" id="email"
                   name="email" placeholder="you@example.com" required minlength="6" maxlength="64">
            <c:if test="${errors.email != null}">
                <small class="text-danger"> ${errors.email}</small>
=======
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
>>>>>>> 8bc685b (Adding as noob)
            </c:if>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password"
<<<<<<< HEAD
                   name="password" required minlength="6" maxlength="16"/>
            <c:if test="${errors.password != null}">
                <small class="text-danger"> ${errors.password}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="passwordConfirmed">
                Password Confirmed
            </label>
            <input type="password" class="form-control" id="passwordConfirmed"
                   name="passwordConfirmed" required minlength="6" maxlength="16"/>
            <c:if test="${errors.passwordConfirmed != null}">
                <small class="text-danger"> ${errors.passwordConfirmed}</small>
=======
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
>>>>>>> 8bc685b (Adding as noob)
            </c:if>
        </div>
        <div class="form-group">
            <label for="email">First Name</label>
            <input type="text" class="form-control" id="firstName"
<<<<<<< HEAD
                   name="firstName" required minlength="1" maxlength="32"/>
            <c:if test="${errors.firstName != null}">
                <small class="text-danger"> ${errors.firstName}</small>
=======
                   value="${userDto.firstName}"
                   name="firstName" placeholder="Ex. Imran"/>
            <c:if test="${errors.firstName != null}">
                <small class="text-danger">${errors.firstName}</small>
>>>>>>> 8bc685b (Adding as noob)
            </c:if>
        </div>
        <div class="form-group">
            <label for="email">Last Name</label>
            <input type="text" class="form-control" id="lastName"
<<<<<<< HEAD
                   name="lastName" required minlength="1" maxlength="32"/>
            <c:if test="${errors.lastName != null}">
                <small class="text-danger"> ${errors.lastName}</small>
=======
                   value="${userDto.lastName}"
                   name="lastName" placeholder="Ex. Hossain"/>
            <c:if test="${errors.lastName != null}">
                <small class="text-danger">${errors.lastName}</small>
>>>>>>> 8bc685b (Adding as noob)
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
<<<<<<< HEAD
<script type="text/javascript">
    function validatePassword() {
        var password = document.getElementById("password").value;
        var cPassword = document.getElementById("passwordConfirmed").value;
        if (password !== cPassword) {
            alert("Password do not match.")
            return false;
        }
        return true;
    }
</script>
<%@include file="includes/footer.jsp"%>

=======
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
>>>>>>> 8bc685b (Adding as noob)
