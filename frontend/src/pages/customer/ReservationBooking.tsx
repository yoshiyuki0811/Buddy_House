import { useState } from 'react';
import { useQuery, useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { petAPI, menuAPI, frameAPI, reservationAPI } from '../../services/api';
import { ReservationType } from '../../types';
import { AlertCircle, CheckCircle2, PawPrint, Calendar } from 'lucide-react';

const inputCls = `w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm
  text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400
  focus:border-sky-400 transition`;

export default function ReservationBooking() {
  const navigate = useNavigate();
  const [reservationType, setReservationType] = useState<ReservationType>(ReservationType.DAYCARE);
  const [selectedMenuId,  setSelectedMenuId]  = useState('');
  const [selectedFrameId, setSelectedFrameId] = useState('');
  const [petIds,          setPetIds]          = useState<number[]>([]);
  const [error,           setError]           = useState('');

  const { data: myPets  } = useQuery({ queryKey: ['myPets'],  queryFn: () => petAPI.getMyList().then((r) => r.data) });
  const { data: menus   } = useQuery({ queryKey: ['menus'],   queryFn: () => menuAPI.getList().then((r) => r.data) });
  const { data: frames  } = useQuery({ queryKey: ['frames'],  queryFn: () => frameAPI.getList().then((r) => r.data) });

  const createMutation = useMutation({
    mutationFn: (data: any) => reservationAPI.create(data),
    onSuccess: () => navigate('/reservations'),
    onError: () => setError('予約の作成に失敗しました。内容を確認してください。'),
  });

  const handlePetToggle = (petId: number) =>
    setPetIds((prev) => prev.includes(petId) ? prev.filter((id) => id !== petId) : [...prev, petId]);

  const handleTypeChange = (type: ReservationType) => {
    setReservationType(type);
    setSelectedMenuId('');
    setSelectedFrameId('');
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    if (petIds.length === 0) { setError('ペットを1頭以上選択してください。'); return; }
    createMutation.mutate({
      reservationFrameId: Number(selectedFrameId),
      menuId: Number(selectedMenuId),
      petIds,
    });
  };

  const filteredMenus  = (menus  ?? []).filter((m: any) => m.reservationType === reservationType);
  const filteredFrames = (frames ?? []).filter((f: any) => f.reservationType === reservationType);

  const fmt = (dt: string) =>
    new Date(dt).toLocaleDateString('ja-JP', { month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' });

  return (
    <div className="max-w-xl space-y-6">

      {/* Header */}
      <div>
        <p className="text-xs font-mono text-zinc-500 mb-1">// 予約フォーム</p>
        <h1 className="text-2xl font-bold text-zinc-100">予約作成</h1>
      </div>

      {error && (
        <div className="flex items-center gap-2 bg-red-400/10 border border-red-400/20 text-red-400 px-4 py-3 rounded-lg text-sm">
          <AlertCircle className="w-4 h-4 shrink-0" />
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-5">

        {/* 予約タイプ */}
        <div className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5">
          <p className="text-xs font-mono text-zinc-400 mb-3">予約タイプ</p>
          <div className="flex gap-3">
            {[
              { value: ReservationType.DAYCARE,   label: '日帰り' },
              { value: ReservationType.OVERNIGHT, label: '宿泊' },
            ].map(({ value, label }) => (
              <button
                key={value}
                type="button"
                onClick={() => handleTypeChange(value)}
                className={`flex-1 py-2.5 rounded-lg text-sm font-medium border transition-all duration-150
                  ${reservationType === value
                    ? 'bg-sky-500 text-white border-sky-500'
                    : 'bg-transparent text-zinc-400 border-zinc-700 hover:border-sky-400/50 hover:text-zinc-200'
                  }`}
              >
                {label}
              </button>
            ))}
          </div>
        </div>

        {/* メニュー選択 */}
        <div className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5">
          <p className="text-xs font-mono text-zinc-400 mb-3">メニュー</p>
          {filteredMenus.length === 0 ? (
            <p className="text-zinc-500 text-sm">このタイプのメニューがありません</p>
          ) : (
            <div className="space-y-2">
              {filteredMenus.map((menu: any) => {
                const active = selectedMenuId === String(menu.id);
                return (
                  <label
                    key={menu.id}
                    className={`flex items-start gap-3 p-4 rounded-lg border cursor-pointer transition-all duration-150
                      ${active ? 'border-sky-400/50 bg-sky-400/5' : 'border-zinc-700 hover:border-sky-400/30'}`}
                  >
                    <div className={`mt-0.5 w-4 h-4 rounded-full border-2 flex items-center justify-center shrink-0
                      ${active ? 'border-sky-400' : 'border-zinc-600'}`}>
                      {active && <div className="w-2 h-2 rounded-full bg-sky-400" />}
                    </div>
                    <input
                      type="radio"
                      name="menuId"
                      value={menu.id}
                      checked={active}
                      onChange={() => setSelectedMenuId(String(menu.id))}
                      className="sr-only"
                      required
                    />
                    <div>
                      <p className="text-sm font-medium text-zinc-100">{menu.name}</p>
                      {menu.feature && <p className="text-xs text-zinc-500 mt-0.5">{menu.feature}</p>}
                    </div>
                  </label>
                );
              })}
            </div>
          )}
        </div>

        {/* 予約枠選択 */}
        <div className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5">
          <p className="text-xs font-mono text-zinc-400 mb-3">予約枠</p>
          {filteredFrames.length === 0 ? (
            <p className="text-zinc-500 text-sm">利用可能な予約枠がありません</p>
          ) : (
            <div className="space-y-2">
              {filteredFrames.map((frame: any) => {
                const available = frame.maxDogs - frame.usedDogs;
                const isFull    = available <= 0;
                const active    = selectedFrameId === String(frame.id);
                return (
                  <label
                    key={frame.id}
                    className={`flex items-start gap-3 p-4 rounded-lg border transition-all duration-150
                      ${isFull
                        ? 'border-zinc-800 opacity-40 cursor-not-allowed'
                        : active
                          ? 'border-sky-400/50 bg-sky-400/5 cursor-pointer'
                          : 'border-zinc-700 hover:border-sky-400/30 cursor-pointer'
                      }`}
                  >
                    <div className={`mt-0.5 w-4 h-4 rounded-full border-2 flex items-center justify-center shrink-0
                      ${active ? 'border-sky-400' : 'border-zinc-600'}`}>
                      {active && <div className="w-2 h-2 rounded-full bg-sky-400" />}
                    </div>
                    <input
                      type="radio"
                      name="frameId"
                      value={frame.id}
                      checked={active}
                      onChange={() => setSelectedFrameId(String(frame.id))}
                      disabled={isFull}
                      className="sr-only"
                      required
                    />
                    <div className="flex-1">
                      <div className="flex items-center gap-2">
                        <Calendar className="w-3.5 h-3.5 text-zinc-400 shrink-0" />
                        <p className="text-sm font-medium text-zinc-100">
                          {fmt(frame.startAt)} 〜 {fmt(frame.endAt)}
                        </p>
                      </div>
                      <p className={`text-xs mt-0.5 font-mono ${isFull ? 'text-red-400' : 'text-green-400'}`}>
                        {isFull ? '満室' : `残り ${available} 頭`}
                      </p>
                    </div>
                  </label>
                );
              })}
            </div>
          )}
        </div>

        {/* ペット選択 */}
        <div className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5">
          <p className="text-xs font-mono text-zinc-400 mb-3">予約するペット</p>
          {!myPets || myPets.length === 0 ? (
            <p className="text-zinc-500 text-sm">
              ペットが登録されていません。先にペット管理からペットを登録してください。
            </p>
          ) : (
            <div className="space-y-2">
              {myPets.map((pet: any) => {
                const checked = petIds.includes(pet.id);
                return (
                  <label
                    key={pet.id}
                    className={`flex items-center gap-3 p-4 rounded-lg border cursor-pointer transition-all duration-150
                      ${checked ? 'border-sky-400/50 bg-sky-400/5' : 'border-zinc-700 hover:border-sky-400/30'}`}
                  >
                    <div className={`w-4 h-4 rounded border-2 flex items-center justify-center shrink-0
                      ${checked ? 'border-sky-400 bg-sky-500' : 'border-zinc-600'}`}>
                      {checked && <CheckCircle2 className="w-3 h-3 text-white" />}
                    </div>
                    <input
                      type="checkbox"
                      checked={checked}
                      onChange={() => handlePetToggle(pet.id)}
                      className="sr-only"
                    />
                    <PawPrint className="w-4 h-4 text-zinc-400 shrink-0" />
                    <div>
                      <p className="text-sm font-medium text-zinc-100">{pet.name}</p>
                      <p className="text-xs text-zinc-500">{pet.breed} / {pet.weight}</p>
                    </div>
                  </label>
                );
              })}
            </div>
          )}
        </div>

        <button
          type="submit"
          disabled={createMutation.isPending || !selectedMenuId || !selectedFrameId}
          className="w-full py-3 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors disabled:opacity-50"
        >
          {createMutation.isPending ? '予約中...' : '予約を確定する'}
        </button>

      </form>
    </div>
  );
}
