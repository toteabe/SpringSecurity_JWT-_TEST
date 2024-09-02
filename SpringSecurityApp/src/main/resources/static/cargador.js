const headers = { 'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb3NlIiwibmJmIjoxNzI1MTE5OTUyLCJpc3MiOiJBVVRIMEpXVC5CQUNLRU5EIiwiZXhwIjoxNzI1MTIxNzUyLCJpYXQiOjE3MjUxMTk5NTIsImF1dGhvcml0aWVzIjoiQ1JFQVRFLERFTEVURSxSRUFELFJFRkFDVE9SLFJPTEVfQURNSU4sUk9MRV9ERVZFTE9QRVIsVVBEQVRFIiwianRpIjoiMDFlOTIwNzUtN2NjOS00NTI3LTg0NTItZmUyNTBhY2YzZjQ4In0.5Jjt9apLjWbdpJqrN66I6pJEbruUAluB17HdNzxyfh8' }; // auth header with bearer token
fetch('http://localhost:8080/personas/nuevo', { headers })
    .then(response => response.text())
    .then(html => {
        document.open();
        document.write(html);
        document.close();  }
    );
