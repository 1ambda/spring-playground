TAG = "MAKE"

.PHONY: open
open:
	@ echo ""
	@ echo ""
	@ echo "[$(TAG)] ($(shell date '+%H:%M:%S')) - Open service UI"
	@ open http://localhost:8080;  # Keycloak UI
	@ echo ""

.PHONY: compose
compose:
	docker compose \
		-f docker-compose.yaml \
		up

.PHONY: compose.clean
compose.clean:
	@ echo ""
	@ echo ""
	@ echo "[$(TAG)] ($(shell date '+%H:%M:%S')) - Cleaning container volumes ('docker/volume')"
	@ docker container prune -f
	@ docker volume prune -f
	@ echo ""
	@ echo ""

