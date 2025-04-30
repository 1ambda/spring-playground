TAG = "MAKE"

.PHONY: run
run:
	@ echo ""
	@ echo ""
	@ echo "[$(TAG)] ($(shell date '+%H:%M:%S')) - Run (http://localhost:3000)"
	@ pmpm run dev

.PHONY: fix
fix:
	@ echo ""
	@ echo ""
	@ echo "[$(TAG)] ($(shell date '+%H:%M:%S')) - Running Eslint Fix"
	@ pnpm lint:fix
	@ pnpm format
