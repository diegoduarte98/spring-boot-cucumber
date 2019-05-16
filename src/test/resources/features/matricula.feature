Feature: the health can be retrieved
  Scenario: client makes call to GET /health
    When O cliente chama por /health
	Then O cliente recebe status code 200