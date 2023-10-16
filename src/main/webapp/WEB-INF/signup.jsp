<%--
  Created by IntelliJ IDEA.
  User: peripatetic
  Date: 10/5/23
  Time: 8:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/headr.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container">
    <br>

    <h2 class="h2">Sign Up</h2>
    <hr class="mb-4">

    <form class="form-horizontal" role="form"
          action="<c:url value="/signup"/>" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username"
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
            </c:if>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password"
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
            </c:if>
        </div>
        <div class="form-group">
            <label for="email">First Name</label>
            <input type="text" class="form-control" id="firstName"
                   name="firstName" required minlength="1" maxlength="32"/>
            <c:if test="${errors.firstName != null}">
                <small class="text-danger"> ${errors.firstName}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="email">Last Name</label>
            <input type="text" class="form-control" id="lastName"
                   name="lastName" required minlength="1" maxlength="32"/>
            <c:if test="${errors.lastName != null}">
                <small class="text-danger"> ${errors.lastName}</small>
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

