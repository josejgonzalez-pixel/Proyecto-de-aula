/**
 * @file components/dashboard/alerts-card.tsx
 * @description Componente de alertas del sistema
 * Muestra notificaciones de advertencia y éxito
 */

'use client';

import { AlertTriangle, CheckCircle2 } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { mockAlerts, type Alert } from '@/lib/data';

/**
 * Props para un elemento de alerta individual
 */
interface AlertItemProps {
  alert: Alert;
}

/**
 * Elemento de alerta individual
 * @param alert - Datos de la alerta a mostrar
 */
function AlertItem({ alert }: AlertItemProps) {
  const isWarning = alert.type === 'warning';
  const isSuccess = alert.type === 'success';
  
  return (
    <div className="flex items-start gap-2">
      {isWarning && (
        <AlertTriangle className="mt-0.5 h-4 w-4 flex-shrink-0 text-warning" />
      )}
      {isSuccess && (
        <CheckCircle2 className="mt-0.5 h-4 w-4 flex-shrink-0 text-success" />
      )}
      <p className="text-sm text-foreground">
        {alert.message}
      </p>
    </div>
  );
}

/**
 * Componente AlertsCard
 * Tarjeta que muestra todas las alertas activas del sistema
 */
export function AlertsCard() {
  return (
    <Card>
      <CardHeader className="pb-3">
        <CardTitle className="text-base font-semibold">Alertas</CardTitle>
      </CardHeader>
      <CardContent className="space-y-3">
        {mockAlerts.map((alert) => (
          <AlertItem key={alert.id} alert={alert} />
        ))}
      </CardContent>
    </Card>
  );
}
