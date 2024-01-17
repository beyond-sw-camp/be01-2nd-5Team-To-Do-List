function sendRequest(address) {
    var jwtToken = getJwtTokenFromCookie("jwtToken");

    // fetch를 사용하여 GET 요청 보내기
    fetch('http://localhost:8080/' + address, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + jwtToken,
            'Content-Type': 'application/json' // 다른 헤더도 필요한 경우 추가
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
    })
    .catch(error => {
        console.error('There has been a problem with your fetch operation:', error);
    });
}

// 요청 보내기
// sendRequest();