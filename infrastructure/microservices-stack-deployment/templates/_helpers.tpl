{{- define "feign.eco-system-urls" -}}
- name: ECO_SERVICE_WAREHOUSE_URL
  value: "https://{{ .Release.Name }}-warehouse-service:{{ .Values.warehouseService.port }}/api/v1/warehouse"
- name: ECO_SERVICE_ORDERS_URL
  value: "https://{{ .Release.Name }}-orders-service:{{ .Values.ordersService.port }}/api/v1/orders"
- name: ECO_SERVICE_PRODUCTS_URL
  value: "https://{{ .Release.Name }}-products-service:{{ .Values.productsService.port }}/api/products"
{{- end }}