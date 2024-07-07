function validatePassword() {
    let passwordField = document.getElementById('password')
    let passwordRepeatField = document.getElementById('repeat-password')
    let passwordNotMatchErr = document.getElementById('password-rep-err')

    if (passwordField.value !== passwordRepeatField.value) {
        passwordNotMatchErr.style.display = 'block'
    } else {
        passwordNotMatchErr.style.display = 'none'
    }
}