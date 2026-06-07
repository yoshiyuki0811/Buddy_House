import { useQuery } from '@tanstack/react-query';
import { customerAPI, petAPI, reservationAPI } from '../../services/api';
import { User, Mail, MapPin, Phone, PawPrint, CalendarDays } from 'lucide-react';

export default function CustomerProfile() {
  const { data: customer, isLoading } = useQuery({
    queryKey: ['customerMe'],
    queryFn: () => customerAPI.getMe().then((r) => r.data),
  });
  const { data: pets } = useQuery({
    queryKey: ['myPets'],
    queryFn: () => petAPI.getMyList().then((r) => r.data),
  });
  const { data: reservations } = useQuery({
    queryKey: ['myReservations'],
    queryFn: () => reservationAPI.getMyList().then((r) => r.data),
  });

  if (isLoading) return (
    <div className="flex items-center justify-center py-24">
      <div className="w-6 h-6 rounded-full border-2 border-sky-400 border-t-transparent animate-spin" />
    </div>
  );

  return (
    <div className="space-y-6 max-w-xl">

      {/* Header */}
      <div>
        <p className="text-xs font-mono text-zinc-500 mb-1">// アカウント</p>
        <h1 className="text-2xl font-bold text-zinc-100">プロフィール</h1>
      </div>

      {/* Profile card */}
      <div className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-6">
        <div className="flex items-center gap-4 mb-6">
          <div className="w-14 h-14 rounded-xl bg-sky-500/10 border border-sky-400/20 flex items-center justify-center text-sky-400 text-xl font-bold">
            {customer?.name?.charAt(0) ?? '?'}
          </div>
          <div>
            <p className="text-lg font-bold text-zinc-100">{customer?.name}</p>
            <p className="text-xs font-mono text-zinc-500">Buddy House メンバー</p>
          </div>
        </div>

        {/* Stats */}
        <div className="grid grid-cols-2 gap-3 mb-6">
          {[
            { icon: CalendarDays, value: reservations?.length ?? 0, label: '予約数' },
            { icon: PawPrint,     value: pets?.length ?? 0,         label: 'ペット数' },
          ].map(({ icon: Icon, value, label }) => (
            <div key={label} className="rounded-lg border border-zinc-700 bg-zinc-900/50 p-4">
              <Icon className="w-4 h-4 text-sky-400 mb-2" />
              <p className="text-2xl font-bold text-zinc-100 font-mono">{value}</p>
              <p className="text-xs text-zinc-500 mt-0.5">{label}</p>
            </div>
          ))}
        </div>

        {/* Account info */}
        <div className="space-y-3 pt-4 border-t border-zinc-700">
          {[
            { icon: Mail,    label: 'メール',   value: customer?.email },
            { icon: MapPin,  label: '住所',     value: customer?.address },
            { icon: Phone,   label: '電話番号', value: customer?.phone },
          ].map(({ icon: Icon, label, value }) => (
            <div key={label} className="flex items-start gap-3">
              <Icon className="w-4 h-4 text-zinc-500 mt-0.5 shrink-0" />
              <div>
                <p className="text-xs font-mono text-zinc-500">{label}</p>
                <p className="text-sm text-zinc-200 mt-0.5">{value ?? '–'}</p>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Pets */}
      {pets && pets.length > 0 && (
        <div className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-6">
          <h2 className="text-sm font-semibold text-zinc-300 mb-4">
            <span className="text-sky-400">#</span> 登録ペット
          </h2>
          <div className="grid grid-cols-1 gap-2">
            {pets.map((pet: any) => (
              <div
                key={pet.id}
                className="flex items-center justify-between rounded-lg border border-zinc-700 bg-zinc-900/30 px-4 py-3"
              >
                <div className="flex items-center gap-3">
                  <PawPrint className="w-4 h-4 text-sky-400 shrink-0" />
                  <div>
                    <p className="text-sm font-medium text-zinc-100">{pet.name}</p>
                    <p className="text-xs text-zinc-500">{pet.breed}</p>
                  </div>
                </div>
                <span className="px-2 py-0.5 rounded-md bg-sky-400/10 text-sky-400 text-xs font-mono border border-sky-400/20">
                  {pet.weight}
                </span>
              </div>
            ))}
          </div>
        </div>
      )}

    </div>
  );
}
