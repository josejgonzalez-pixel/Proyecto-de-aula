/**
 * @file components/dashboard/transactions-table.tsx
 * @description Tabla de transacciones recientes
 * Muestra las últimas transacciones con categoría, monto y cuenta
 */

'use client';

import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { mockTransactions, formatCurrency, getCategoryColor } from '@/lib/data';
import { cn } from '@/lib/utils';

/**
 * Componente TransactionsTable
 * Tabla responsive que muestra las transacciones recientes
 * En mobile muestra una versión simplificada
 */
export function TransactionsTable() {
  return (
    <Card>
      <CardHeader className="pb-3">
        <CardTitle className="text-base font-semibold">
          Transacciones Recientes
        </CardTitle>
      </CardHeader>
      <CardContent className="p-0 lg:p-6 lg:pt-0">
        {/* Vista Desktop */}
        <div className="hidden lg:block">
          <Table>
            <TableHeader>
              <TableRow className="hover:bg-transparent">
                <TableHead className="text-xs font-medium text-muted-foreground">
                  Data
                </TableHead>
                <TableHead className="text-xs font-medium text-muted-foreground">
                  Descripción
                </TableHead>
                <TableHead className="text-xs font-medium text-muted-foreground">
                  Categoría
                </TableHead>
                <TableHead className="text-xs font-medium text-muted-foreground">
                  Monto
                </TableHead>
                <TableHead className="text-xs font-medium text-muted-foreground">
                  Cuenta
                </TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {mockTransactions.slice(0, 4).map((transaction) => (
                <TableRow key={transaction.id} className="hover:bg-muted/50">
                  <TableCell className="text-sm">{transaction.date}</TableCell>
                  <TableCell className="text-sm">{transaction.description}</TableCell>
                  <TableCell className="text-sm">{transaction.category}</TableCell>
                  <TableCell>
                    <div className="flex items-center gap-2">
                      <span
                        className="h-2 w-2 rounded-full"
                        style={{ backgroundColor: getCategoryColor(transaction.category) }}
                      />
                      <span className={cn(
                        'text-sm font-medium',
                        transaction.type === 'income' ? 'text-income' : 'text-foreground'
                      )}>
                        {transaction.type === 'income' ? '+' : ''}${formatCurrency(transaction.amount)}
                      </span>
                    </div>
                  </TableCell>
                  <TableCell className="text-sm text-muted-foreground">
                    {transaction.account}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>
        
        {/* Vista Mobile */}
        <div className="lg:hidden">
          <Table>
            <TableHeader>
              <TableRow className="hover:bg-transparent">
                <TableHead className="px-4 text-xs font-medium text-muted-foreground">
                  Data
                </TableHead>
                <TableHead className="px-2 text-xs font-medium text-muted-foreground">
                  Descripción
                </TableHead>
                <TableHead className="px-2 text-xs font-medium text-muted-foreground">
                  Categoría
                </TableHead>
                <TableHead className="px-4 text-xs font-medium text-muted-foreground">
                  Monto
                </TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {mockTransactions.slice(0, 4).map((transaction) => (
                <TableRow key={transaction.id} className="hover:bg-muted/50">
                  <TableCell className="px-4 text-xs">{transaction.date}</TableCell>
                  <TableCell className="max-w-[100px] truncate px-2 text-xs">
                    {transaction.description}
                  </TableCell>
                  <TableCell className="px-2 text-xs">{transaction.category}</TableCell>
                  <TableCell className="px-4">
                    <div className="flex items-center gap-1">
                      <span
                        className="h-1.5 w-1.5 rounded-full"
                        style={{ backgroundColor: getCategoryColor(transaction.category) }}
                      />
                      <span className="text-xs font-medium">
                        ${formatCurrency(transaction.amount)}
                      </span>
                    </div>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>
      </CardContent>
    </Card>
  );
}
