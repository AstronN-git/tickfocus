function validatePassword() {
    let passwordField = document.getElementById('password')
    let passwordRepeatField = document.getElementById('repeat-password')
    let passwordNotMatchErr = document.getElementById('password-rep-err')

    console.log(passwordField.value)
    console.log(passwordRepeatField.value)

    if (passwordField.value !== passwordRepeatField.value) {
        passwordNotMatchErr.style.display = 'block'
    } else {
        passwordNotMatchErr.style.display = 'none'
    }
}