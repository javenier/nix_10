$(document).ready(function () {
    $('#searchField').autocomplete({
            source: function (request, response) {
                $.get("http://localhost:8080/catalogue/suggestions?", {query: request.term}, function (data, status) {
                    $("#results").html("");
                    if (status === 'success') {
                        response(data);
                    }
                });
            }
        }
    );
})