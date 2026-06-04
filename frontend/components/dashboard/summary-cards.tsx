/**
 * @file components/dashboard/summary-cards.tsx
 * @description Tarjetas de resumen financiero
 * Muestra Saldo Total, Ingresos del Mes y Gastos del Mes
 */

'use client';

import { TrendingUp, TrendingDown } from 'lucide-react';
import { Card, CardContent } from '@/components/ui/card';
import { formatCurrency } from '@/lib/data';
import { cn } from '@/lib/utils';
import { useDashboardData } from '@/hooks/use-dashboard-data';
import { Skeleton } from '../ui/skeleton';


/**
 * Props para una tarjeta de resumen individual
 */
interface SummaryCardProps {
  title: string;
  amount: number;
  type?: 'total' | 'income' | 'expense';
  showTrend?: boolean;
  trendLabel?: string;
}

/**
 * Tarjeta de resumen individual
 * @param title - Título de la tarjeta
 * @param amount - Monto a mostrar
 * @param type - Tipo de tarjeta para estilos
 * @param showTrend - Mostrar indicador de tendencia
 * @param trendLabel - Etiqueta del indicador
 */
function SummaryCard({ title, amount, type = 'total', showTrend, trendLabel }: SummaryCardProps) {
  const isIncome = type === 'income';
  const isExpense = type === 'expense';
  const isTotal = type === 'total';
  
  return (
    <Card className={cn(
      'relative overflow-hidden',
      isTotal && 'bg-primary text-primary-foreground'
    )}>
      <CardContent className="p-4">
        <p className={cn(
          'text-xs font-medium',
          isTotal ? 'text-emerald-100' : 'text-muted-foreground'
        )}>
          {title}
        </p>
        <p className={cn(
          'mt-1 font-bold tracking-tight text-base sm:text-lg md:text-xl lg:text-2xl flex flex-wrap items-baseline gap-1',
          isTotal && 'text-primary-foreground'
        )}>
          ${formatCurrency(amount)} <span className="text-sm font-normal">COP</span>
        </p>
        
        {showTrend && (
          <div className={cn(
            'mt-2 flex items-center gap-1 text-xs font-medium',
            isIncome && 'text-emerald-400 dark:text-emerald-400',
            isExpense && 'text-red-500 dark:text-red-400'
          )}>
            {isIncome ? (
              <TrendingUp className="h-3 w-3" />
            ) : (
              <TrendingDown className="h-3 w-3" />
            )}
            <span>{trendLabel}</span>
          </div>
        )}
      </CardContent>
    </Card>
  );
}

/**
 * Componente SummaryCards
 * Grid de tarjetas de resumen financiero
 */
export function SummaryCards() {
  const { saldo, ingresos, gastos, loading } = useDashboardData();

console.log("daotos en componte:",{saldo, ingresos, gastos});

  if (loading) {
    return (
      <div className="grid grid-cols-3 gap-3 lg:gap-4">
        {[1, 2, 3].map((i) => (
          <div key={i} className="h-24 rounded-xl border p-4">
            <Skeleton className="h-4 w-20 mb-2" />
            <Skeleton className="h-8 w-full" />
          </div>
        ))}
      </div>
    );
  }

  return (
    <div className="grid grid-cols-3  md:grid-cols-3 gap-3 lg:gap-4">
      <SummaryCard
        title="Saldo Total"
        amount={saldo}
        type="total"
      />
      <SummaryCard
        title="Ingresos del Mes"
        amount={ingresos}
        type="income"
        showTrend
        trendLabel="Ingreso"
      />
      <SummaryCard
        title="Gastos del Mes"
        amount={gastos}
        type="expense"
        showTrend
        trendLabel="Gasto"
      />
    </div>
  );
}
