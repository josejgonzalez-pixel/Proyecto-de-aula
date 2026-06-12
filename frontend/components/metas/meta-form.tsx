import { 
  Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription, DialogTrigger 
} from '@/components/ui/dialog';
import { Plus } from 'lucide-react';

export function NuevaMetaDialog({ onGuardar }: { onGuardar: () => void }) {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <button className="bg-primary text-primary-foreground px-4 py-2 rounded-lg font-semibold flex items-center gap-2 hover:bg-primary/90">
          <Plus className="h-4 w-4" /> Nueva Meta
        </button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Registrar Nueva Meta</DialogTitle>
          <DialogDescription>
            Completa los datos para definir tu nuevo objetivo de ahorro.
          </DialogDescription>
        </DialogHeader>
        
        <form onSubmit={(e) => { /* Aquí tu lógica de fetch */ }} className="space-y-4">
          {/* Tus inputs: nombreMeta, montoMeta, fechaLimite, etc. */}
        </form>
      </DialogContent>
    </Dialog>
  );
}