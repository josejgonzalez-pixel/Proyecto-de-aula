/**
 * @file app/(pages)/cuentas/page.tsx
 * @description Página de gestión de cuentas bancarias
 */

import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Plus, Wallet, CreditCard, Building2 } from 'lucide-react';

const accounts = [
  { id: 1, name: 'Cuenta Principal', type: 'Ahorros', balance: 1250000, icon: Wallet, color: 'bg-primary' },
  { id: 2, name: 'Tarjeta de Crédito', type: 'Crédito', balance: -350000, icon: CreditCard, color: 'bg-accent' },
  { id: 3, name: 'Cuenta Nómina', type: 'Corriente', balance: 2500000, icon: Building2, color: 'bg-chart-2' },
];

export default function CuentasPage() {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold">Cuentas</h1>
          <p className="text-sm text-muted-foreground">Gestiona tus cuentas bancarias</p>
        </div>
        <Button>
          <Plus className="mr-2 h-4 w-4" />
          Nueva Cuenta
        </Button>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
        {accounts.map((account) => {
          const Icon = account.icon;
          return (
            <Card key={account.id} className="cursor-pointer transition-shadow hover:shadow-md">
              <CardHeader className="flex flex-row items-center gap-4 pb-2">
                <div className={`flex h-10 w-10 items-center justify-center rounded-lg ${account.color} text-white`}>
                  <Icon className="h-5 w-5" />
                </div>
                <div>
                  <CardTitle className="text-base">{account.name}</CardTitle>
                  <p className="text-xs text-muted-foreground">{account.type}</p>
                </div>
              </CardHeader>
              <CardContent>
                <p className={`text-2xl font-bold ${account.balance < 0 ? 'text-destructive' : ''}`}>
                  ${account.balance.toLocaleString('es-CO')} COP
                </p>
              </CardContent>
            </Card>
          );
        })}
      </div>
    </div>
  );
}
