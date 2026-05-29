/**
 * @file app/(pages)/dashboard/page.tsx
 * @description Página principal del Dashboard de FinanziApp
 * * Renderiza los widgets financieros dentro del layout compartido de (pages).
 * * @component Dashboard
 */

import {
  SummaryCards,
  AlertsCard,
  CashFlowChart,
  TransactionsTable,
  CategoryChart,
  QuickAddButton,
  QuickAddCard,
} from '@/components/dashboard';

export default function Dashboard() {
  return (
    <>
      {/* Título mobile */}
      <h1 className="mb-4 text-xl font-bold lg:hidden">Dashboard</h1>
      
      {/* Grid principal del dashboard */}
      <div className="grid gap-4 lg:grid-cols-3 lg:gap-6">
        {/* Columna principal (2/3 en desktop) */}
        <div className="space-y-4 lg:col-span-2 lg:space-y-6">
          {/* Tarjetas de resumen */}
          <SummaryCards />
          
          {/* Alertas - Solo visible en mobile */}
          <div className="lg:hidden">
            <AlertsCard />
          </div>
          
          {/* Gráfico de flujo de efectivo */}
          <CashFlowChart />
          
          {/* Tabla de transacciones */}
          <TransactionsTable />
        </div>
        
        {/* Sidebar derecho (1/3 en desktop) */}
        <div className="hidden space-y-4 lg:block lg:space-y-6">
          {/* Alertas */}
          <AlertsCard />
          
          {/* Gráfico de categorías */}
          <CategoryChart />
          
          {/* Formulario de registro rápido */}
          <QuickAddCard />
        </div>
      </div>
      
      {/* Botón flotante Quick Add - Solo mobile */}
      <QuickAddButton />
    </>
  );
}